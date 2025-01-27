package pro.progr.owlgame.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.imageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.web.Map
import pro.progr.owlgame.data.web.MapApiService
import pro.progr.owlgame.data.web.inpouch.MapInPouchModel
import java.io.File
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MapsRepository @Inject constructor(
    private val apiService: MapApiService,
    private val mapDao: MapDao,
    private val context: Context,
    @Named("apiKey") private val apiKey: String
) {

    suspend fun getMaps(): Result<List<Map>> {
        //todo: будут сохраняться в хранилище и получаться из локальных файлов
        //либо по сети, если нет локальных файлов?
        //либо сверять то, что локально, и то, что по сети?
        return try {
            val response = apiService.getMaps(apiKey)
            if (response.isSuccessful) {
                val mapUrls = response.body() ?: emptyList()
                val maps = mapUrls.map { mapUrl -> Map("", "Болотистая местность", mapUrl) }
                Result.success(maps)
            } else {
                Result.failure(Exception("Failed to load maps: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getMapById(id: String): Flow<MapEntity?> {
        return mapDao.getMapById(id)
    }

    suspend fun saveImageLocally(imageUrl: String): String {
        val fileName = imageUrl.substringAfterLast("/")
        val file = File(context.filesDir, fileName)

        if (!file.exists()) {
            try {
                val request = ImageRequest.Builder(context)
                    .data(imageUrl)
                    .build()

                // Выполняем запрос через imageLoader
                val result = context.imageLoader.execute(request)

                // Проверяем результат
                val drawable = when (result) {
                    is coil.request.SuccessResult -> result.drawable
                    is coil.request.ErrorResult -> throw Exception("Failed to load image: ${result.throwable.message}")
                    else -> throw Exception("Unexpected result from image request")
                }

                // Сохраняем изображение локально
                val bitmap = (drawable as BitmapDrawable).bitmap
                file.outputStream().use { outputStream: OutputStream ->
                    bitmap.compress(Bitmap.CompressFormat.WEBP, 100, outputStream)
                }
            } catch (e: Exception) {
                throw Exception("Error saving image locally: ${e.message}", e)
            }
        }

        return file.absolutePath
    }

    suspend fun saveMaps(maps: List<MapEntity>) {
        mapDao.insertMaps(maps)
    }

}

