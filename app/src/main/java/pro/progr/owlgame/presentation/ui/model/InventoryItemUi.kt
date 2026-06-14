package pro.progr.owlgame.presentation.ui.model

data class InventoryItemUi(
    val id: String,
    val title: String,
    val imageUrl: String,
    val amount: Int? = null
)