package pro.progr.owlgame.data.repository.impl

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.dao.AnimalDao
import pro.progr.owlgame.data.db.dao.EnemyDao
import pro.progr.owlgame.data.db.dao.ExpeditionDao
import pro.progr.owlgame.data.db.dao.ExpeditionMedalDao
import pro.progr.owlgame.data.db.dao.ExpeditionWithDataDao
import pro.progr.owlgame.data.db.dao.MapDao
import pro.progr.owlgame.data.db.dao.SuppliesDao
import pro.progr.owlgame.data.db.model.AnimalStatus
import pro.progr.owlgame.data.db.model.EffectType
import pro.progr.owlgame.data.db.model.MapType
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.model.ExpeditionStatus
import pro.progr.owlgame.data.util.BattleResolver
import pro.progr.owlgame.domain.model.ExpeditionModel
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel
import pro.progr.owlgame.domain.model.StartExpeditionRequest
import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import java.time.Clock
import javax.inject.Inject

private const val BATTLE_TICK_MILLIS = 2L * 60 * 1000 // 2 минуты
private const val FUTURE_TOLERANCE = 1L * 60 * 1000   // 1 минута
private const val FUTURE_RESET_THRESHOLD = 10L * 60 * 1000   // 10 минут
private const val FUGITIVE_DURATION_MILLIS = 14L * 24 * 60 * 60 * 1000 // 14 дней

class ExpeditionsRepositoryImpl @Inject constructor(
    private val expeditionWithDataDao: ExpeditionWithDataDao,
    private val appDatabase: OwlGameDatabase,
    private val expeditionsDao: ExpeditionDao,
    private val suppliesDao: SuppliesDao,
    private val mapsDao: MapDao,
    private val animalDao: AnimalDao,
    private val enemyDao: EnemyDao,
    private val expeditionMedalDao: ExpeditionMedalDao,
    private val resolveBattle: BattleResolver,
    private val clock: Clock
) : ExpeditionsRepository {

    private val mutex = Mutex()

    override fun getExpeditionWithData(mapId: String): Flow<ExpeditionWithDataModel?> {
        return expeditionWithDataDao.getExpeditionWithData(mapId)
            .map { expedition -> expedition?.toDomain() }
    }

    override suspend fun startExpedition(
        diamondDao: PurchaseInterface,
        request: StartExpeditionRequest
    ): Result<Unit> = mutex.withLock {
        runCatching {
            require(request.extraHeal >= 0) { "Heal не может быть отрицательным" }
            require(request.extraDamage >= 0) { "Damage не может быть отрицательным" }

            val expedition = expeditionsDao.getById(request.expeditionId)
                ?: error("Экспедиция не найдена")

            val animal = animalDao.getAnimalById(request.animalId)
                ?: error("Животное не найдено")

            check(animal.status == AnimalStatus.PET) {
                "Животное больше нельзя отправить в экспедицию"
            }

            val selectedIds = request.selectedSupplies.map { it.supplyId }
            val supplies = suppliesDao.getByIds(selectedIds).associateBy { it.id }

            request.selectedSupplies.forEach { selected ->
                require(selected.amount > 0) {
                    "Количество припаса должно быть больше нуля"
                }

                val supply = requireNotNull(supplies[selected.supplyId]) {
                    "Припас не найден"
                }

                check(supply.amount >= selected.amount) {
                    "Недостаточно припаса: ${supply.name}"
                }
            }

            val totalHeal = request.selectedSupplies.sumOf { selected ->
                val supply = supplies.getValue(selected.supplyId)
                if (supply.effectType == EffectType.HEAL) {
                    selected.amount * supply.effectAmount
                } else {
                    0
                }
            } + request.extraHeal

            val totalDamage = request.selectedSupplies.sumOf { selected ->
                val supply = supplies.getValue(selected.supplyId)
                if (supply.effectType == EffectType.DAMAGE) {
                    selected.amount * supply.effectAmount
                } else {
                    0
                }
            } + request.extraDamage

            val diamondCost = request.extraHeal + request.extraDamage

            if (diamondCost > 0) {
                val spendResult = diamondDao.spendDiamonds(diamondCost)
                if (spendResult.isFailure || spendResult.getOrNull() != true) {
                    error("Не удалось списать бриллианты")
                }
            }

            appDatabase.withTransaction {
                request.selectedSupplies.forEach { selected ->
                    val updatedRows = suppliesDao.consume(selected.supplyId, selected.amount)
                    check(updatedRows == 1) {
                        "Не удалось списать припас ${selected.supplyId}"
                    }
                }

                val animalRows = animalDao.updateStatusIfCurrent(
                    animalId = request.animalId,
                    newStatus = AnimalStatus.EXPEDITION,
                    expectedOldStatus = AnimalStatus.PET
                )
                check(animalRows == 1) {
                    "Животное больше недоступно для экспедиции"
                }

                val expeditionRows = expeditionsDao.updateStartData(
                    expeditionId = expedition.id,
                    healAmount = totalHeal,
                    damageAmount = totalDamage,
                    maxHealAmount = totalHeal,
                    maxDamageAmount = totalDamage,
                    animalId = request.animalId,
                    status = ExpeditionStatus.ACTIVE,
                    lastBattleUpdateTime = clock.millis()
                )
                check(expeditionRows == 1) {
                    "Не удалось обновить экспедицию"
                }

                val mapRows = mapsDao.updateType(
                    mapId = request.mapId,
                    type = MapType.EXPEDITION
                )
                check(mapRows == 1) {
                    "Не удалось обновить карту"
                }

                Unit
            }
        }
    }

    override suspend fun getById(expeditionId: String) : ExpeditionModel? {
        return expeditionsDao.getById(expeditionId)?.toDomain()
    }

    override suspend fun updateAnimalId(
        expeditionId: String,
        animalId: String?
    ): Int {
        return expeditionsDao.updateAnimalId(expeditionId, animalId)
    }

    override suspend fun resolveExpeditionProgress(
        expeditionId: String
    ): Result<Unit> = mutex.withLock {
        runCatching {
            val now = clock.millis()

            val expeditionWithData = expeditionWithDataDao.getExpeditionWithDataById(expeditionId)
                ?: error("Экспедиция не найдена")

            val expedition = expeditionWithData.expedition

            if (expedition.status != ExpeditionStatus.ACTIVE) {
                return@runCatching
            }

            if (expedition.lastBattleUpdateTime == null)
                error("У активной экспедиции не задано время последнего обновления")

            //защита на случай перевода часов назад
            val futureDelta = expedition.lastBattleUpdateTime - now

            if (futureDelta > FUTURE_RESET_THRESHOLD) {
                val rows = expeditionsDao.updateLastBattleUpdateTime(
                    expeditionId = expedition.id,
                    lastBattleUpdateTime = now
                )
                check(rows == 1) { "Не удалось ресинхронизировать время битвы" }
                return@runCatching
            }

            if (futureDelta > FUTURE_TOLERANCE) {
                return@runCatching
            }

            val elapsed = now - expedition.lastBattleUpdateTime
            val availableTicks = elapsed / BATTLE_TICK_MILLIS

            if (availableTicks <= 0L) {
                return@runCatching
            }

            val resolution = resolveBattle(
                expedition = expedition,
                enemies = expeditionWithData.enemies,
                availableTicks = availableTicks
            )

            val newLastUpdateTime =
                expedition.lastBattleUpdateTime + resolution.appliedTicks * BATTLE_TICK_MILLIS

            appDatabase.withTransaction {
                enemyDao.updateEnemies(resolution.updatedEnemies)

                expeditionsDao.updateBattleState(
                    expeditionId = expedition.id,
                    healAmount = resolution.expeditionHeal,
                    damageAmount = resolution.expeditionDamage,
                    animalId = expedition.animalId,
                    status = resolution.expeditionStatus,
                    lastBattleUpdateTime = newLastUpdateTime
                )

                mapsDao.updateType(
                    mapId = expedition.mapId,
                    type = resolution.mapType
                )

                expedition.animalId?.let { animalId ->
                    applyAnimalStatus(animalId, resolution.animalStatus, now)

                    if (resolution.medalForAnimal) expeditionMedalDao.updateAnimalId(
                        animalId = animalId,
                        expeditionId = expedition.id)
                }

                Unit
            }
        }
    }

    private suspend fun applyAnimalStatus(
        animalId: String,
        status: AnimalStatus,
        now: Long
    ) {
        when (status) {
            AnimalStatus.EXPEDITION -> {
                val rows = animalDao.updateStatusWithUntilIfCurrent(
                    animalId = animalId,
                    newStatus = AnimalStatus.EXPEDITION,
                    expectedOldStatus = AnimalStatus.EXPEDITION,
                    statusExpiresAt = null
                )
                check(rows == 1) { "Не удалось подтвердить статус EXPEDITION у животного" }
            }

            AnimalStatus.PET -> {
                val rows = animalDao.updateStatusWithUntilIfCurrent(
                    animalId = animalId,
                    newStatus = AnimalStatus.PET,
                    expectedOldStatus = AnimalStatus.EXPEDITION,
                    statusExpiresAt = null
                )
                check(rows == 1) { "Не удалось вернуть животное в PET" }
            }

            AnimalStatus.FUGITIVE -> {
                val rows = animalDao.updateStatusWithUntilIfCurrent(
                    animalId = animalId,
                    newStatus = AnimalStatus.FUGITIVE,
                    expectedOldStatus = AnimalStatus.EXPEDITION,
                    statusExpiresAt = now + FUGITIVE_DURATION_MILLIS
                )
                check(rows == 1) { "Не удалось перевести животное в FUGITIVE" }
            }

            else -> {
                error("Неподдерживаемый статус для applyAnimalStatus: $status")
            }
        }
    }

    override fun getLootAvailableExpedition(mapId: String): Flow<ExpeditionWithDataModel?> {
        return expeditionWithDataDao
            .getExpeditionWithDataByStatus(mapId, ExpeditionStatus.LOOT_AVAILABLE)
            .map { it?.toDomain() }
    }

    override fun claimExpeditionLoot(expeditionId: String) {
        TODO("Not yet implemented")
    }

}

