package pro.progr.owlgame.data.repository

import android.util.Log
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
                    slots = mapWithData.slots
                )
            }
        }
    }

    fun getMapsWithUninhabitedBuildings() : Flow<List<MapWithData>> {
        return mapsWithDataDao.getMapsWithUninhabitedBuildings()
    }

    suspend fun updateMapsFromServer() {
        var result = try {
            val response = apiService.getMaps(apiKey)
            if (response.isSuccessful) {
                val maps = response.body() ?: emptyList()
                Result.success(maps)
            } else {
                Result.failure(Exception("Failed to load maps: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

        if (result.isSuccess) {

            //сохранение в базе?
        }
    }

    fun getMapById(id: String): Flow<MapWithData?> {
        Log.wtf("Map id: ", id)
        return mapsWithDataDao.getMapWithData(id)
    }

    suspend fun saveMaps(maps: List<MapEntity>) {
        mapDao.insertMaps(maps)
    }

}

