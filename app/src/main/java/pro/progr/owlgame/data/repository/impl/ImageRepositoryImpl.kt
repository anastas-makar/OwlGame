package pro.progr.owlgame.data.repository.impl

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import pro.progr.owlgame.domain.repository.ImageRepository
import java.io.File
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val context: Context
) : ImageRepository {

    override suspend fun saveImageLocally(
        imageUrl: String,
        imageKey: String
    ): String = withContext(Dispatchers.IO) {

        require(
            imageUrl.startsWith("http://") ||
                    imageUrl.startsWith("https://")
        ) {
            "Expected absolute image URL, got: $imageUrl"
        }

        require(
            imageKey.isNotBlank() &&
                    !imageKey.startsWith("/") &&
                    ".." !in imageKey &&
                    '\\' !in imageKey
        ) {
            "Invalid imageKey: $imageKey"
        }

        val imagesDir = File(context.filesDir, "images")
        val file = File(imagesDir, imageKey)

        file.parentFile?.let { parent ->
            check(parent.exists() || parent.mkdirs()) {
                "Failed to create image directory: ${parent.absolutePath}"
            }
        }

        if (file.exists()) {
            return@withContext file.absolutePath
        }

        val tempFile = File(
            file.parentFile,
            "${file.name}.part"
        )

        try {
            val request = Request.Builder()
                .url(imageUrl)
                .get()
                .build()

            httpClient.newCall(request).execute().use { response ->
                check(response.isSuccessful) {
                    "HTTP ${response.code} while loading $imageUrl"
                }

                val body = checkNotNull(response.body) {
                    "Empty response body while loading $imageUrl"
                }

                body.byteStream().use { input ->
                    tempFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }

            check(tempFile.renameTo(file)) {
                "Failed to move downloaded image to ${file.absolutePath}"
            }

            file.absolutePath

        } catch (e: Exception) {
            tempFile.delete()

            throw Exception(
                "Failed to load image: " +
                        "imageUrl=$imageUrl, imageKey=$imageKey; ${e.message}",
                e
            )
        }
    }

    companion object {
        private val httpClient = OkHttpClient()
    }
}
