package pro.progr.owlgame.domain.repository

interface ImageRepository {
    suspend fun saveImageLocally(
        imageUrl: String,
        imageKey: String
    ): String

}

