package pro.progr.owlgame.domain.model

data class MerchantPricePolicyModel(
    val firstPrice: Int,
    val increasePerPurchase: Int
) {
    fun priceForPurchaseCount(purchaseCount: Int): Int {
        return firstPrice + purchaseCount * increasePerPurchase
    }
}