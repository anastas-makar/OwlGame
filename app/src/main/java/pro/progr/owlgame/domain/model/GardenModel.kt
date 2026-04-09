package pro.progr.owlgame.domain.model

data class GardenModel(
    val id : String,
    val name : String,
    val imageUrl : String,
    val buildingId : String,
    val gardenNumber : Int,
    val gardenType : GardenType)
