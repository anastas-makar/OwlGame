package pro.progr.owlgame.presentation.ui.model

import androidx.annotation.StringRes

data class InventoryCategoryUi(
    val type: InventoryCategoryType,
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    val items: List<InventoryItemUi>
)
