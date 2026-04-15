package pro.progr.owlgame.data.web.inpouch

data class InPouch(
    val buildings : List<BuildingInPouch> = emptyList(),
    val maps : List<MapInPouch> = emptyList(),
    val diamonds : DiamondsInPouch? = null,
    val gardenItems : List<GardenItemInPouch> = emptyList(),
    val plants : List<PlantInPouch> = emptyList(),
    val furniture : List<FurnitureInPouch> = emptyList(),
    val recipes : List<RecipeInPouch> = emptyList()
)