package pro.progr.owlgame.domain.model

data class LocationWithScenesModel(
    val id : String,
    val name : String,
    val description : String,
    val imageUrl : String,
    val mapId : String? = null,
    val price : Int = 0,
    val x : Float = 0f,
    val y : Float = 0f,
    val type: LocationType,
    val scenes: List<LocationSceneModel>
)