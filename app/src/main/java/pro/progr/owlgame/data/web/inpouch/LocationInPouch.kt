package pro.progr.owlgame.data.web.inpouch

import pro.progr.owlgame.data.model.LocationType

data class LocationInPouch(
    val id : String,
    val name : String,
    val description : String,
    val imageUrl : String,
    val price : Int = 0,
    val x : Float = 0f,
    val y : Float = 0f,
    val type: LocationType,
    val scenes: List<LocationSceneInPouch>)
