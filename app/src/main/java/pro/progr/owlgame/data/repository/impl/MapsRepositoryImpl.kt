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
import pro.progr.owlgame.data.db.embedded.MapWithData
import pro.progr.owlgame.data.db.dao.MapWithDataDao
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.presentation.ui.model.MapData
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(
    private val mapDao: MapDao,
    private val mapsWithDataDao: MapWithDataDao,
    private val expeditionDao: ExpeditionDao,
    private val enemyDao: EnemyDao,
    private val database: OwlGameDatabase
) : MapsRepository {

    override fun getMaps(): Flow<List<MapData>> {
        return mapsWithDataDao.getMapsWithData().map { mapsList ->
            mapsList.map {mapWithData ->
                MapData(
                    mapWithData.mapEntity.id,
                    mapWithData.mapEntity.name,
                    mapWithData.mapEntity.imagePath,
                    mapWithData.mapEntity.type,
                    buildings = emptyList()
                )
            }
        }
    }

    override fun getMapsWithUninhabitedBuildings() : Flow<List<MapWithData>> {
        return mapsWithDataDao.getMapsWithUninhabitedBuildings()
    }

    override fun getMapById(id: String): Flow<MapWithData?> {
        return mapsWithDataDao.getMapWithData(id)
    }

    override suspend fun saveMaps(maps: List<MapEntity>,
                                  expeditions: List<Expedition>,
                                  enemies: List<Enemy>) {
        database.withTransaction {
            mapDao.insertMaps(maps)
            expeditionDao.insert(expeditions)
            enemyDao.insert(enemies)
        }
    }

    override suspend fun setTown(name: String, mapId: String) {
        mapDao.setTown(mapId = mapId, name = name)
    }

}

