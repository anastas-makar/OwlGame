package pro.progr.owlgame.domain.model

data class MapWithBuildingsModel (
    val id: String,
    val name: String,
    val imageUrl: String,
    val type: MapType,
    val buildings: List<BuildingModel> = emptyList(),
    val expeditionId: String? = null
)