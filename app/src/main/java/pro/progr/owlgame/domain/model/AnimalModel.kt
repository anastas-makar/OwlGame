package pro.progr.owlgame.domain.model

data class AnimalModel(
    val id: String,
    val kind: String,
    val name: String?,
    val initialDisplayName: String,
    val imagePath: String,
    val status: AnimalStatus,
    val statusExpiresAt: Long? = null
)