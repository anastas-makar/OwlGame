package pro.progr.owlgame.presentation.ui.model

data class LootItemUi(
    val id: String,
    val title: String,
    val description: String? = null,
    val hint: String? = null,
    val imageUrl: String? = null,
    val iconRes: Int? = null,
    val amount: Int? = null,
    val route: String? = null
)