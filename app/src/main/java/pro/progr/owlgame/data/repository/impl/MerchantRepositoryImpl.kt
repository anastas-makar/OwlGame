package pro.progr.owlgame.data.repository.impl

import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.web.LootApiService
import pro.progr.owlgame.domain.model.MerchantShopModel
import pro.progr.owlgame.domain.repository.MerchantRepository
import pro.progr.owlgame.diagnostics.OwlGameApiException
import javax.inject.Inject

class MerchantRepositoryImpl @Inject constructor(private val apiService: LootApiService) : MerchantRepository {
    override suspend fun getMerchantShop(): MerchantShopModel {
        val response = apiService.getMerchantShop()

        if (!response.isSuccessful) {
            throw OwlGameApiException("merchant_shop", response.code())
        }

        return response.body()?.toDomain()
            ?: throw OwlGameApiException("merchant_shop_empty_response")
    }
}
