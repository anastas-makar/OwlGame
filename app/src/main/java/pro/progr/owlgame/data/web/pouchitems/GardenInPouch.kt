package pro.progr.owlgame.data.web.pouchitems

import pro.progr.owlgame.data.db.model.GardenType

data class GardenInPouch (
    val id : String,
    val name : String,
    val imageUrl : String,
    val gardenNumber : Int,
    val gardenType : GardenType,
    val templateId: String? = null,
    val imageKey: String? = null
)