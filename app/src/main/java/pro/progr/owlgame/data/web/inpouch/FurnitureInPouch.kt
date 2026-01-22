package pro.progr.owlgame.data.web.inpouch

import pro.progr.owlgame.data.db.FurnitureType

data class FurnitureInPouch (
    val id : String,
    val name : String,
    val price : Int = 0,
    val imageUrl : String,
    val height : Float,
    val width : Float,
    val type : FurnitureType
)