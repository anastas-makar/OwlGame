package pro.progr.owlgame.domain.model

data class MapWithDataModel (
    val id: String,
    val name: String,
    val imageUrl: String,
    val type: MapType,
    val countryId: String? = null,
    val buildings: List<BuildingWithAnimalModel> = emptyList(),
    val streets: List<StreetWithBuildingsModel> = emptyList(),
    val expedition: ExpeditionWithDataModel? = null,
    val locations : List<LocationWithScenesModel> = emptyList()
)