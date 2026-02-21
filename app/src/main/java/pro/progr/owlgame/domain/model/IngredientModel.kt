package pro.progr.owlgame.domain.model

data class IngredientModel(
    val supplyId: String,
    val name: String,
    val imageUrl: String,
    val required: Int,
    val have: Int
) {
    val enough: Boolean get() = have >= required
}
