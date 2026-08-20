package pro.progr.owlgame.data.web

data class Map(
    val id: String,
    val name: String,
    val imageUrl: String,
    val templateId: String? = null,
    val imageKey: String? = null
)
