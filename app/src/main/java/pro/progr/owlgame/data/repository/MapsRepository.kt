package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.db.MapWithData
import pro.progr.owlgame.data.db.MapWithDataDao
import pro.progr.owlgame.data.web.MapApiService
import pro.progr.owlgame.presentation.ui.model.MapData
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MapsRepository @Inject constructor(
    private val apiService: MapApiService,
    private val mapDao: MapDao,
    private val mapsWithDataDao: MapWithDataDao,
    @Named("apiKey") private val apiKey: String
) {

    fun getMaps(): Flow<List<MapData>> {
        //todo: синхронизация с данными пользователя на сервере, когда данные о картах пользователя будут там сохраняться

        return mapsWithDataDao.getMapsWithData().map { mapsList ->
            mapsList.map {mapWithData ->
                MapData(
                    mapWithData.mapEntity.id,
                    mapWithData.mapEntity.name,
                    mapWithData.mapEntity.imagePath,
                    town = mapWithData.town,
                    buildings = emptyList() //todo: для отображения карт они вроде не нужны сейчас
                )
            }
        }
    }

    fun getMapsWithUninhabitedBuildings() : Flow<List<MapWithData>> {
        return mapsWithDataDao.getMapsWithUninhabitedBuildings()
    }

    fun getMapById(id: String): Flow<MapWithData?> {
        return mapsWithDataDao.getMapWithData(id)
    }

    suspend fun saveMaps(maps: List<MapEntity>) {
        mapDao.insertMaps(maps)
    }

}

