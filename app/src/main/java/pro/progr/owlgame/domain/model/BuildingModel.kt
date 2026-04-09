package pro.progr.owlgame.domain.model


data class BuildingModel (
    val id : String,
    val name : String,
    val imageUrl : String,
    val mapId: String?,
    val price : Int = 500,
    var animalId: String? = null,
    val x : Float = 0f,
    val y : Float = 0f,
    val type: BuildingType
)