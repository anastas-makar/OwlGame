package pro.progr.owlgame.data.web.inpouch

data class PlantInPouch (
    val id : String,
    val name : String,
    val description : String,
    val imageUrl : String,
    val supply : SupplyInPouch,
    val supplyAmount : Int,
    val seedAmount : Int,
    val seedName : String
)