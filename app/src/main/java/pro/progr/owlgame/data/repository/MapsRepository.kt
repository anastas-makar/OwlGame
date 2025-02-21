package pro.progr.owlgame.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import coil.imageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.db.MapWithData
import pro.progr.owlgame.data.db.MapWithDataDao
import pro.progr.owlgame.data.web.Map
import pro.progr.owlgame.data.web.MapApiService
import pro.progr.owlgame.presentation.ui.model.MapData
import java.io.File
import java.io.OutputStream
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
        //todo: будут сохраняться в хранилище и получаться из локальных файлов
        //либо по сети, если нет локальных файлов?
        //либо сверять то, что локально, и то, что по сети?

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

