package pro.progr.owlgame.domain.model

data class StreetWithBuildingsModel (
    val id: String?,
    val name: String,
    val direction: StreetDirection,
    val isMain: Boolean = false,
    val buildings: List<BuildingWithAnimalModel>
)