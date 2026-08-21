package pro.progr.owlgame.domain.model

data class LocationSceneModel(
    val id : String,
    val name : String? = null,
    val description: String,
    val imageUrl : String,
    val locationId : String,
    val sceneNumber : Int,
    val questId : String?,
    val questButtonText : String?,
    val templateId: String,
    val imageKey: String)
