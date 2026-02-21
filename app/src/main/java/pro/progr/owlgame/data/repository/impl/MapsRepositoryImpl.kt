package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.db.MapWithData
import pro.progr.owlgame.data.db.MapWithDataDao
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.presentation.ui.model.MapData
import javax.inject.Inject

class MapsRepositoryImpl @Inject constructor(
    private val mapDao: MapDao,
    private val mapsWithDataDao: MapWithDataDao
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

    override suspend fun saveMaps(maps: List<MapEntity>) {
        mapDao.insertMaps(maps)
    }

    override suspend fun setTown(name: String, mapId: String) {
        mapDao.setTown(mapId = mapId, name = name)
    }

}

