package pro.progr.owlgame.data.web.pouchitems

data class RecipeInPouch(
    val id: String,
    val description: String,
    val resultSupply: SupplyInPouch,
    val ingredients: List<IngredientInPouch>
)
