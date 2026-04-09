package pro.progr.owlgame.domain.model

data class RecipeWithSuppliesModel(
    val recipeId: String,
    val resultSupply: SupplyModel,
    val resultName: String,
    val resultImageUrl: String,
    val description: String,
    val ingredients: List<IngredientWithSupplyModel>,
    val craftable: Boolean
)
