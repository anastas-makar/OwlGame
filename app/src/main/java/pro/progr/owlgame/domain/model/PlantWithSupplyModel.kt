package pro.progr.owlgame.domain.model

data class PlantWithSupplyModel(
    val id : String,
    val name : String,
    val description : String,
    val imageUrl : String,
    val supply : SupplyModel,
    val supplyAmount : Int,
    val seedAmount : Int)
