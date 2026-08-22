package pro.progr.owlgame.data.repository.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.imageLoader
import coil.request.ImageRequest
import pro.progr.owlgame.domain.repository.ImageRepository
import java.io.File
import java.io.OutputStream
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val context: Context
) : ImageRepository {
    override suspend fun saveImageLocally(
        imageUrl: String,
        imageKey: String
    ): String {
        require(imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
            "Expected absolute image URL, got: $imageUrl"
        }

        require(!imageKey.startsWith("/") && ".." !in imageKey) {
            "Invalid imageKey: $imageKey"
        }

        val imagesDir = File(context.filesDir, "images")
        val file = File(imagesDir, imageKey)

        file.parentFile?.mkdirs()

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
                throw Exception("Failed to load image: imageUrl=$imageUrl, imageKey=$imageKey; ${e.message}")
            }
        }

        return file.absolutePath
    }

}

