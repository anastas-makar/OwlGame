package pro.progr.owlgame.data.web.inpouch

import pro.progr.owlgame.data.db.model.GardenType
import pro.progr.owlgame.data.db.model.ItemType

data class GardenItemInPouch (
    val id : String,
    val name : String,
    val description : String,
    val imageUrl : String,
    val supply : SupplyInPouch,
    val supplyAmount : Int,
    val itemType : ItemType,
    val gardenType: GardenType
)