package pro.progr.owlgame.domain.model

data class InPouchModel(
    val buildings : List<BuildingWithDataModel> = emptyList(),
    val maps : List<MapWithDataModel> = emptyList(),
    val diamonds : DiamondsModel? = null,
    val gardenItems : List<GardenItemWithSupplyModel> = emptyList(),
    val plants : List<PlantWithSupplyModel> = emptyList(),
    val furniture : List<FurnitureModel> = emptyList(),
    val recipes : List<RecipeWithSuppliesModel> = emptyList())
