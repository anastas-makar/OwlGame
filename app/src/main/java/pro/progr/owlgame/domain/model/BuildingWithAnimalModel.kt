package pro.progr.owlgame.domain.model


data class BuildingWithAnimalModel (
    val id : String,
    val name : String,
    val imageUrl : String,
    val mapId: String?,
    val price : Int = 500,
    val streetId: String? = null,
    var animal: AnimalModel? = null,
    val x : Float = 0f,
    val y : Float = 0f,
    val type: BuildingType
)