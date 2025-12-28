package pro.progr.owlgame.data.web.inpouch

import pro.progr.owlgame.data.db.GardenType

data class GardenInPouch (
    val id : String,
    val name : String,
    val imageUrl : String,
    val gardenNumber : Int,
    val gardenType : GardenType
)