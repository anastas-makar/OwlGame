package pro.progr.owlgame.data.web.inpouch

data class InPouch(
    val buildings: List<BuildingInPouch>? = null,
    val maps: List<MapInPouch>? = null,
    val diamonds: DiamondsInPouch? = null,
    val gardenItems: List<GardenItemInPouch>? = null,
    val plants: List<PlantInPouch>? = null,
    val furniture: List<FurnitureInPouch>? = null,
    val recipes: List<RecipeInPouch>? = null,
    val locations: List<LocationInPouch>? = null
)