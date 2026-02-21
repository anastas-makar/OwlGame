package pro.progr.owlgame.presentation.ui.model

data class RecipeUi(
    val receiptId: String,
    val resultSupplyId: String,
    val resultName: String,
    val resultImageUrl: String,
    val description: String,
    val ingredients: List<IngredientUi>,
    val craftable: Boolean
)
