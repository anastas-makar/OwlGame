package pro.progr.owlgame.data.mapper

import pro.progr.owlgame.data.web.AnimalApiModel
import pro.progr.owlgame.data.web.merchant.MerchantPricePolicyApiModel
import pro.progr.owlgame.data.web.merchant.MerchantShopApiModel
import pro.progr.owlgame.data.web.quest.QuestApiModel
import pro.progr.owlgame.data.web.quest.QuestOptionApiModel
import pro.progr.owlgame.data.web.quest.QuestPageApiModel
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.AnimalStatus
import pro.progr.owlgame.domain.model.MerchantPricePolicyModel
import pro.progr.owlgame.domain.model.MerchantShopModel
import pro.progr.owlgame.domain.model.QuestModel
import pro.progr.owlgame.domain.model.QuestOptionModel
import pro.progr.owlgame.domain.model.QuestPageModel

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

fun QuestApiModel.toDomain(): QuestModel {
    return QuestModel(
        questId = questId,
        title = title,
        startPageNumber = startPageNumber,
        pages = pages.map { it.toDomain() }
    )
}

fun QuestPageApiModel.toDomain(): QuestPageModel {
    return QuestPageModel(
        number = number,
        name = name,
        description = description,
        imageUrl = imageUrl,
        options = options.map { it.toDomain() },
        endingId = endingId
    )
}

fun QuestOptionApiModel.toDomain(): QuestOptionModel {
    return QuestOptionModel(
        description = description,
        targetPageNumber = targetPageNumber
    )
}