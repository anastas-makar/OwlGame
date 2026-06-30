package pro.progr.owlgame.domain.repository

import pro.progr.owlgame.domain.model.MerchantShopModel

interface MerchantRepository {
    suspend fun getMerchantShop(): MerchantShopModel?
}