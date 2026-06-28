package pro.progr.owlgame.data.web.merchant

import pro.progr.owlgame.data.web.pouchitems.PouchItemsDto

data class MerchantShopDto(
    val id: String,
    val title: String? = null,
    val description: String? = null,
    val pricePolicy: MerchantPricePolicyDto,
    val items: PouchItemsDto
)