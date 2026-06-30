package pro.progr.owlgame.data.mapper

import pro.progr.owlgame.data.web.AnimalApiModel
import pro.progr.owlgame.data.web.merchant.MerchantPricePolicyApiModel
import pro.progr.owlgame.data.web.merchant.MerchantShopApiModel
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.AnimalStatus
import pro.progr.owlgame.domain.model.MerchantPricePolicyModel
import pro.progr.owlgame.domain.model.MerchantShopModel

fun AnimalApiModel.toDomain() =
    AnimalModel(
        id = id,
        kind = kind,
        name = null,
        initialDisplayName = initialDisplayName,
        imagePath = imagePath,
        status = AnimalStatus.SEARCHING,
        statusExpiresAt = null
    )

fun MerchantPricePolicyApiModel.toDomain() =
    MerchantPricePolicyModel(
        firstPrice = firstPrice,
        increasePerPurchase = increasePerPurchase
    )

fun MerchantShopApiModel.toDomain() =
    MerchantShopModel (
        id = id,
        title = title,
        description = description,
        pricePolicy = pricePolicy.toDomain(),
        items = items.toDomain()
    )