package pro.progr.owlgame.domain.model

data class GardenItemWithSupplyModel (
    val id : String,
    val name : String,
    val description : String,
    val imageUrl : String,
    val supply : SupplyModel,
    val supplyAmount : Int,
    val itemType : ItemType,
    val gardenType: GardenType
)