package pro.progr.owlgame.presentation.ui.model.merchant

data class MerchantShopUiState(
    val title: String? = null,
    val description: String? = null,
    val diamonds: Int = 0,
    val currentPrice: Int = 0,
    val sections: List<MerchantShopSectionUi> = emptyList(),
    val isLoading: Boolean = false,
    val isBuying: Boolean = false,
    val errorMessage: String? = null
) {
    val hasEnoughDiamonds: Boolean
        get() = diamonds >= currentPrice
}

