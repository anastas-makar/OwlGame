package pro.progr.owlgame.data.web.inpouch

import pro.progr.owlgame.data.db.EffectType

data class RecipeInPouch(
    val id: String,
    val description: String,
    val effectType : EffectType,
    val effectAmount : Int,
    val resultSupply: SupplyInPouch,
    val ingredients: List<IngredientInPouch>
)
