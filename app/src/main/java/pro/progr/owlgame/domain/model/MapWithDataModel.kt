package pro.progr.owlgame.domain.model

data class MapWithDataModel (
    val id: String,
    val name: String,
    val imageUrl: String,
    val type: MapType,
    val buildings: List<BuildingWithAnimalModel> = emptyList(),
    val expedition: ExpeditionModel? = null
)