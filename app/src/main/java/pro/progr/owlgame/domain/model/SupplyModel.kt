package pro.progr.owlgame.domain.model

data class SupplyModel(
    val id : String,
    val imageUrl : String,
    val name : String,
    val description : String,
    val amount : Int = 0,
    val effectType : EffectType,
    val effectAmount : Int)
