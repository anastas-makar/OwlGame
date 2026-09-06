package pro.progr.owlgame.data.repository.impl

import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.web.LootApiService
import pro.progr.owlgame.domain.model.MerchantShopModel
import pro.progr.owlgame.domain.repository.MerchantRepository
import javax.inject.Inject

class MerchantRepositoryImpl @Inject constructor(private val apiService: LootApiService) : MerchantRepository {
    override suspend fun getMerchantShop(): MerchantShopModel {
        val response = apiService.getMerchantShop()

        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string()
            throw IllegalStateException(
                "Failed to load merchant shop: HTTP ${response.code()}: $errorBody"
            )
        }

        return response.body()?.toDomain()
            ?: throw IllegalStateException("Merchant shop response body is empty")
    }
}
