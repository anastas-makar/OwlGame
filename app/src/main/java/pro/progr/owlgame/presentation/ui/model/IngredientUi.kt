package pro.progr.owlgame.presentation.ui.model

data class IngredientUi(
    val supplyId: String,
    val name: String,
    val imageUrl: String,
    val required: Int,
    val have: Int
) {
    val enough: Boolean get() = have >= required
}
