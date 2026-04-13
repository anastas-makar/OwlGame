package pro.progr.owlgame.domain.model

data class PlantModel(
    val id : String,
    val name : String,
    val description : String,
    val imageUrl : String,
    val gardenId : String? = null,
    val x : Float = 0f,
    val y : Float = 0f,
    val supplyId : String,
    val supplyAmount : Int,
    val seedAmount : Int,
    val readiness: Float = 0f,
    val deleted: Boolean = false)
