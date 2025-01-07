package pro.progr.owlgame.data.web.inpouch

data class BuildingInPouch (
    val id : String,
    val type: BuildingType,
    val cost: Int,
    val name: String,
    val imageUrl: String
)