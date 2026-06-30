package pro.progr.owlgame.data.web.merchant

import pro.progr.owlgame.data.web.pouchitems.PouchItemsDto

data class MerchantShopApiModel(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val pricePolicy: MerchantPricePolicyApiModel,
    val items: PouchItemsDto
)