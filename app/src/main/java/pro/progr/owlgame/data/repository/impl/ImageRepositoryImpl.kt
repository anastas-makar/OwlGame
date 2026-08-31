package pro.progr.owlgame.data.repository.impl

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import pro.progr.owlgame.domain.repository.ImageRepository
import java.io.File
import javax.inject.Inject
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ConcurrentHashMap

class ImageRepositoryImpl @Inject constructor(
    private val context: Context
) : ImageRepository {

    override suspend fun saveImageLocally(
        imageUrl: String,
        imageKey: String
    ): String {
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

        val mutex = imageMutexes.getOrPut(imageKey) {
            Mutex()
        }

        return mutex.withLock {
            saveImageLocallyLocked(
                imageUrl = imageUrl,
                imageKey = imageKey
            )
        }
    }

    private suspend fun saveImageLocallyLocked(
        imageUrl: String,
        imageKey: String
    ): String = withContext(Dispatchers.IO) {

        val imagesDir = File(
            context.filesDir,
            "images"
        )

        val file = File(
            imagesDir,
            imageKey
        )

        val parent = checkNotNull(file.parentFile) {
            "Image has no parent directory: $imageKey"
        }

        // mkdirs() может вернуть false, если другую директорию
        // уже создали параллельно. Поэтому проверяем итоговое состояние.
        parent.mkdirs()

        check(parent.isDirectory) {
            "Failed to create image directory: ${parent.absolutePath}"
        }

        // Пока мы держим mutex конкретного imageKey,
        // второй поток не сможет одновременно качать тот же файл.
        if (file.isFile) {
            return@withContext file.absolutePath
        }

        val tempFile = File.createTempFile(
            "${file.name}.",
            ".part",
            parent
        )

        try {
            val request = Request.Builder()
                .url(imageUrl)
                .get()
                .build()

            httpClient
                .newCall(request)
                .execute()
                .use { response ->

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
                "Failed to move downloaded image " +
                        "from ${tempFile.absolutePath} " +
                        "to ${file.absolutePath}"
            }

            file.absolutePath

        } catch (e: Exception) {
            tempFile.delete()

            throw Exception(
                "Failed to load image: " +
                        "imageUrl=$imageUrl, " +
                        "imageKey=$imageKey; " +
                        "${e.message}",
                e
            )
        }
    }

    companion object {

        private val httpClient = OkHttpClient()

        private val imageMutexes =
            ConcurrentHashMap<String, Mutex>()
    }
}
