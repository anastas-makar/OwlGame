package pro.progr.owlgame.data.web.inpouch

import pro.progr.owlgame.data.db.GardenType
import pro.progr.owlgame.data.db.ItemType

data class GardenItemInPouch (
    val id : String,
    val name : String,
    val description : String,
    val imageUrl : String,
    val supplyName : String,
    val supplyAmount : Int,
    val itemType : ItemType,
    val gardenType: GardenType
)