package pro.progr.owlgame.data.repository.impl

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.entity.Enemy
import pro.progr.owlgame.data.db.dao.EnemyDao
import pro.progr.owlgame.data.db.entity.Expedition
import pro.progr.owlgame.data.db.dao.ExpeditionDao
import pro.progr.owlgame.data.db.dao.MapDao
import pro.progr.owlgame.data.db.entity.MapEntity
import pro.progr.owlgame.data.db.dao.MapWithDataDao
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.embedded.MapWithData
import pro.progr.owlgame.data.mapper.toData
import pro.progr.owlgame.domain.model.MapType
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.domain.model.MapModel
import pro.progr.owlgame.domain.model.MapWithBuildingsModel
import pro.progr.owlgame.domain.model.MapWithDataModel
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.domain.repository.ImageRepository
import javax.inject.Inject
import kotlin.collections.map
import kotlin.collections.plusAssign

class MapsRepositoryImpl @Inject constructor(
    private val mapDao: MapDao,
    private val mapsWithDataDao: MapWithDataDao,
    private val expeditionDao: ExpeditionDao,
    private val enemyDao: EnemyDao,
    private val database: OwlGameDatabase
) : MapsRepository {

    override fun getMaps(): Flow<List<MapModel>> {
        return mapDao.getMaps().map { mapsList ->
            mapsList.map {map ->
                map.toDomain()
            }
        }
    }

    override fun getMapsWithUninhabitedBuildings(): Flow<List<MapWithBuildingsModel>> {
        return mapsWithDataDao.getMapsWithUninhabitedBuildings()
            .map { mapsList -> mapsList.map(MapWithData::toDomain) }
    }

    override fun getMapById(id: String): Flow<MapWithBuildingsModel?> {
        return mapsWithDataDao.getMapWithData(id).map { mapWithData ->
            mapWithData?.toDomain()
        }
    }

    override suspend fun saveMaps(mapModels: List<MapWithDataModel>) {
        val mapEntities = mutableListOf<MapEntity>()
        val expeditionEntities = mutableListOf<Expedition>()
        val enemyEntities = mutableListOf<Enemy>()

        mapModels.forEach { mapModel ->

            mapEntities += MapEntity(
                id = mapModel.id,
                name = mapModel.name,
                imagePath = mapModel.imageUrl,
                type = mapModel.type.toData()
            )

            if (mapModel.type == MapType.OCCUPIED) {
                val expeditionModel = requireNotNull(mapModel.expedition) {
                    "Map ${mapModel.id} has type OCCUPIED but expedition is null"
                }

                expeditionEntities += Expedition(
                    id = expeditionModel.id,
                    title = expeditionModel.title,
                    description = expeditionModel.description,
                    mapId = mapModel.id,
                    healAmount = 0,
                    damageAmount = 0,
                    animalId = null
                )

                enemyEntities += expeditionModel.enemies.map { enemyModel ->
                    Enemy(
                        id = enemyModel.id,
                        expeditionId = expeditionModel.id,
                        name = enemyModel.name,
                        description = enemyModel.description,
                        imageUrl = enemyModel.imageUrl,
                        healAmount = enemyModel.healAmount,
                        damageAmount = enemyModel.damageAmount,
                        x = enemyModel.x,
                        y = enemyModel.y
                    )
                }
            }
        }

        database.withTransaction {
            mapDao.insertMaps(mapEntities)
            expeditionDao.insert(expeditionEntities)
            enemyDao.insert(enemyEntities)
        }
    }

    override suspend fun setTown(name: String, mapId: String) {
        mapDao.setTown(mapId = mapId, name = name)
    }

}

