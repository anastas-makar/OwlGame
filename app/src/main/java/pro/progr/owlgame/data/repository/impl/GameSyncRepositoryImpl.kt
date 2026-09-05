package pro.progr.owlgame.data.repository.impl

import androidx.room.withTransaction
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.GAME_INSTANCE_ID_META_KEY
import pro.progr.owlgame.data.db.INITIAL_RESTORE_COMPLETED_META_KEY
import pro.progr.owlgame.data.db.dao.AppMetaDao
import pro.progr.owlgame.data.db.dao.GameSyncDao
import pro.progr.owlgame.data.db.dao.OutboxDao
import pro.progr.owlgame.data.mapper.*
import pro.progr.owlgame.data.web.sync.GameBackupRequest
import pro.progr.owlgame.data.web.sync.GameRestoreRequest
import pro.progr.owlgame.data.web.sync.GameSyncApiService
import pro.progr.owlgame.data.web.sync.GameSyncData
import pro.progr.owlgame.data.web.sync.GameSyncMetaData
import pro.progr.owlgame.domain.model.GameSyncResult
import pro.progr.owlgame.domain.repository.GameSyncRepository
import pro.progr.owlgame.domain.repository.ImageRepository
import javax.inject.Inject

class GameSyncRepositoryImpl @Inject constructor(
    private val db: OwlGameDatabase,
    private val syncDao: GameSyncDao,
    private val outboxDao: OutboxDao,
    private val appMetaDao: AppMetaDao,
    private val apiService: GameSyncApiService,
    private val imageRepository: ImageRepository
) : GameSyncRepository {

    override suspend fun sync(): GameSyncResult = syncMutex.withLock {
        if (!isInitialRestoreCompleted()) {
            restoreInitialSnapshot()
        } else {
            backupChanges()
        }
    }

    private suspend fun restoreInitialSnapshot(): GameSyncResult {
        val response = apiService.restore(
            GameRestoreRequest(syncMetaData = metaData())
        )

        if (!response.isSuccessful) {
            error("Restore HTTP ${response.code()}: ${response.errorBody()?.string().orEmpty()}")
        }

        val restore = response.body() ?: error("Empty restore body")
        val data = restore.data

        // Картинки скачиваем до Room-транзакции: транзакцию нельзя держать во время сети.
        val localImages = downloadRestoreImages(data)

        db.withTransaction {
            // Порядок важен из-за foreign keys.
            syncDao.insertCountries(data.countries.map { it.toEntity() })
            syncDao.insertAnimals(data.animals.map { it.toEntity(localImages.getValue(it.imageKey)) })
            syncDao.insertMaps(data.maps.map { it.toEntity(localImages.getValue(it.imageKey)) })
            syncDao.insertStreets(data.streets.map { it.toEntity() })
            syncDao.insertBuildings(data.buildings.map { it.toEntity(localImages.getValue(it.imageKey)) })
            syncDao.insertRooms(data.rooms.map { it.toEntity(localImages.getValue(it.imageKey)) })
            syncDao.insertGardens(data.gardens.map { it.toEntity(localImages.getValue(it.imageKey)) })
            syncDao.insertSupplies(data.supplies.map { it.toEntity(localImages.getValue(it.imageKey)) })
            syncDao.insertRecipes(data.recipes.map { it.toEntity() })
            syncDao.insertSupplyToRecipes(data.supplyToRecipes.map { it.toEntity() })
            syncDao.insertPlants(data.plants.map { it.toEntity(localImages.getValue(it.imageKey)) })
            syncDao.insertGardenItems(data.gardenItems.map { it.toEntity(localImages.getValue(it.imageKey)) })
            syncDao.insertFurniture(data.furniture.map { it.toEntity(localImages.getValue(it.imageKey)) })
            syncDao.insertLocations(data.locations.map { it.toEntity(localImages.getValue(it.imageKey)) })
            syncDao.insertLocationScenes(data.locationScenes.map { it.toEntity(localImages.getValue(it.imageKey)) })
            syncDao.insertExpeditions(data.expeditions.map { it.toEntity() })
            syncDao.insertEnemies(data.enemies.map { it.toEntity(localImages.getValue(it.imageKey)) })
            syncDao.insertExpeditionMedals(data.expeditionMedals.map { it.toEntity(localImages.getValue(it.imageKey)) })

            // INSERT-триггеры во время restore создадут outbox. Эти записи уже есть
            // на сервере в snapshot, поэтому отправлять их обратно не надо.
            outboxDao.clearAll()
            appMetaDao.setValue(INITIAL_RESTORE_COMPLETED_META_KEY, "1")
        }

        return GameSyncResult.RESTORED
    }

    private suspend fun backupChanges(): GameSyncResult {
        // Фиксируем именно тот набор outbox, который будем подтверждать после HTTP 200.
        // Новые записи, появившиеся во время запроса, останутся в таблице.
        val outboxes = outboxDao.getSync()
        if (outboxes.isEmpty()) return GameSyncResult.NOTHING_TO_BACKUP

        val byTable = outboxes
            .groupBy { it.tableName }
            .mapValues { (_, rows) -> rows.map { it.rowId }.distinct() }

        val data = GameSyncData(
            animals = rows(byTable, "animals") { syncDao.getAnimals(it) }.map { it.toSyncDto() },
            buildings = rows(byTable, "buildings") { syncDao.getBuildings(it) }.map { it.toSyncDto() },
            countries = rows(byTable, "countries") { syncDao.getCountries(it) }.map { it.toSyncDto() },
            enemies = rows(byTable, "enemies") { syncDao.getEnemies(it) }.map { it.toSyncDto() },
            expeditions = rows(byTable, "expeditions") { syncDao.getExpeditions(it) }.map { it.toSyncDto() },
            expeditionMedals = rows(byTable, "expedition_medals") { syncDao.getExpeditionMedals(it) }.map { it.toSyncDto() },
            furniture = rows(byTable, "furniture") { syncDao.getFurniture(it) }.map { it.toSyncDto() },
            gardens = rows(byTable, "gardens") { syncDao.getGardens(it) }.map { it.toSyncDto() },
            gardenItems = rows(byTable, "garden_items") { syncDao.getGardenItems(it) }.map { it.toSyncDto() },
            locations = rows(byTable, "locations") { syncDao.getLocations(it) }.map { it.toSyncDto() },
            locationScenes = rows(byTable, "location_scenes") { syncDao.getLocationScenes(it) }.map { it.toSyncDto() },
            maps = rows(byTable, "maps") { syncDao.getMaps(it) }.map { it.toSyncDto() },
            plants = rows(byTable, "plants") { syncDao.getPlants(it) }.map { it.toSyncDto() },
            recipes = rows(byTable, "recipes") { syncDao.getRecipes(it) }.map { it.toSyncDto() },
            rooms = rows(byTable, "rooms") { syncDao.getRooms(it) }.map { it.toSyncDto() },
            streets = rows(byTable, "streets") { syncDao.getStreets(it) }.map { it.toSyncDto() },
            supplies = rows(byTable, "supplies") { syncDao.getSupplies(it) }.map { it.toSyncDto() },
            supplyToRecipes = rows(byTable, "supply_to_recipe") { syncDao.getSupplyToRecipes(it) }.map { it.toSyncDto() }
        )

        val response = apiService.backup(
            GameBackupRequest(
                syncMetaData = metaData(),
                data = data
            )
        )

        if (!response.isSuccessful) {
            error("Backup HTTP ${response.code()}: ${response.errorBody()?.string().orEmpty()}")
        }

        val result = response.body() ?: error("Empty backup body")
        check(result.accepted) { "Server did not accept game backup" }

        db.withTransaction {
            outboxDao.clearSync(outboxes)
        }

        return GameSyncResult.BACKED_UP
    }

    private suspend fun metaData(): GameSyncMetaData = GameSyncMetaData(
        gameInstanceId = requireNotNull(appMetaDao.getValue(GAME_INSTANCE_ID_META_KEY)) {
            "Game database has no game_instance_id"
        },
        dbVersion = db.openHelper.readableDatabase.version
    )

    private suspend fun isInitialRestoreCompleted(): Boolean =
        appMetaDao.getValue(INITIAL_RESTORE_COMPLETED_META_KEY) == "1"

    private suspend fun <T> rows(
        byTable: Map<String, List<String>>,
        tableName: String,
        loader: suspend (List<String>) -> List<T>
    ): List<T> {
        val ids = byTable[tableName].orEmpty()
        return if (ids.isEmpty()) emptyList() else loader(ids)
    }

    private suspend fun downloadRestoreImages(data: GameSyncData): Map<String, String> {
        val refs = linkedMapOf<String, String>()

        fun add(imageKey: String, imageUrl: String?) {
            val url = requireNotNull(imageUrl) {
                "Restore response has no imageUrl for imageKey=$imageKey"
            }
            val previous = refs.putIfAbsent(imageKey, url)
            require(previous == null || previous == url) {
                "Restore contains different URLs for imageKey=$imageKey: $previous and $url"
            }
        }

        data.animals.forEach { add(it.imageKey, it.imageUrl) }
        data.buildings.forEach { add(it.imageKey, it.imageUrl) }
        data.enemies.forEach { add(it.imageKey, it.imageUrl) }
        data.expeditionMedals.forEach { add(it.imageKey, it.imageUrl) }
        data.furniture.forEach { add(it.imageKey, it.imageUrl) }
        data.gardens.forEach { add(it.imageKey, it.imageUrl) }
        data.gardenItems.forEach { add(it.imageKey, it.imageUrl) }
        data.locations.forEach { add(it.imageKey, it.imageUrl) }
        data.locationScenes.forEach { add(it.imageKey, it.imageUrl) }
        data.maps.forEach { add(it.imageKey, it.imageUrl) }
        data.plants.forEach { add(it.imageKey, it.imageUrl) }
        data.rooms.forEach { add(it.imageKey, it.imageUrl) }
        data.supplies.forEach { add(it.imageKey, it.imageUrl) }

        val semaphore = Semaphore(4)
        return coroutineScope {
            refs.map { (imageKey, imageUrl) ->
                async {
                    semaphore.withPermit {
                        imageKey to imageRepository.saveImageLocally(imageUrl, imageKey)
                    }
                }
            }.awaitAll().toMap()
        }
    }

    companion object {
        private val syncMutex = Mutex()
    }
}
