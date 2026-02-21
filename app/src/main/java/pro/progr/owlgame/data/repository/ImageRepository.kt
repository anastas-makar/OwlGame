package pro.progr.owlgame.data.repository

interface ImageRepository {
    suspend fun saveImageLocally(imageUrl: String): String

}

