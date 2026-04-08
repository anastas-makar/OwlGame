package pro.progr.owlgame.domain.model


data class BuildingWithAnimalModel (
    val id : String,
    val name : String,
    val imageUrl : String,
    val price : Int = 500,
    var animal: AnimalModel? = null,
    val x : Float = 0f,
    val y : Float = 0f,
    val type: BuildingType
)