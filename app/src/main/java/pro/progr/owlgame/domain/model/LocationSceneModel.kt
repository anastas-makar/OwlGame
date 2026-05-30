package pro.progr.owlgame.domain.model

data class LocationSceneModel(
    val id : String,
    val name : String? = null,
    val description: String,
    val imageUrl : String,
    val locationId : String,
    val sceneNumber : Int)
