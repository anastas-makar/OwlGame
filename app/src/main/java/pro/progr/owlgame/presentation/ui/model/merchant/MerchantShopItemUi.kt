package pro.progr.owlgame.presentation.ui.model.merchant

data class MerchantShopItemUi(
    val key: MerchantItemKey,
    val title: String,
    val description: String?,
    val imageUrl: String,
    val extraInfo: MerchantItemExtraInfo? = null
)