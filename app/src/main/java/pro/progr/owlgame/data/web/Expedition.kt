package pro.progr.owlgame.data.web

data class Expedition(
    val id: String,
    val title: String,
    val description: String,
    val mapId: String,
    val enemies: List<Enemy>
)
