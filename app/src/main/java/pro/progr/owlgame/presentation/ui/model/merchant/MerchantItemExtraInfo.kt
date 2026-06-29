package pro.progr.owlgame.presentation.ui.model.merchant

sealed class MerchantItemExtraInfo {
    data class BuildingCost(val amount: Int) : MerchantItemExtraInfo()
    data class FurnitureInstallCost(val amount: Int) : MerchantItemExtraInfo()
    data class SeedAmount(val amount: Int) : MerchantItemExtraInfo()
    data object RecipeUnlock : MerchantItemExtraInfo()
}