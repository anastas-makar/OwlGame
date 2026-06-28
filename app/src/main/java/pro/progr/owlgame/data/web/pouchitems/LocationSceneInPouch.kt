package pro.progr.owlgame.data.web.pouchitems

data class LocationSceneInPouch(
    val id : String,
    val name : String? = null,
    val description: String,
    val imageUrl : String,
    val sceneNumber : Int)
