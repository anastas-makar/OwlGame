package pro.progr.owlgame.data.repository.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.imageLoader
import coil.request.ImageRequest
import pro.progr.owlgame.data.repository.ImageRepository
import java.io.File
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Named

class ImageRepositoryImpl @Inject constructor(
    private val context: Context,
    @Named("baseUrl") private val baseUrl: String
) : ImageRepository {
    override suspend fun saveImageLocally(imageUrl: String): String {
        val fileName = imageUrl.substringAfterLast("/")
        val file = File(context.filesDir, fileName)

        if (!file.exists()) {
            try {
                val request = ImageRequest.Builder(context)
                    .data(baseUrl + imageUrl)
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

}

