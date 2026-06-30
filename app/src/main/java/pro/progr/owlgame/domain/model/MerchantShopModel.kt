package pro.progr.owlgame.domain.model

data class MerchantShopModel(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val pricePolicy: MerchantPricePolicyModel,
    val items: PouchItemsModel
)