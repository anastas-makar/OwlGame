package pro.progr.owlgame.domain.model

data class RecipeModel(
    val recipeId: String,
    val resultSupplyId: String,
    val resultName: String,
    val resultImageUrl: String,
    val description: String,
    val ingredients: List<IngredientModel>,
    val craftable: Boolean
)
