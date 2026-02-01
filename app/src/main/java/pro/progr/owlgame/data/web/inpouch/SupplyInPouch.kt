package pro.progr.owlgame.data.web.inpouch

import pro.progr.owlgame.data.db.EffectType

data class SupplyInPouch (
    val id : String,
    val imageUrl : String,
    val name : String,
    val description : String,
    val effectType : EffectType,
    val effectAmount : Int
)