package pro.progr.owlgame.data.repository.impl

import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.web.LootApiService
import pro.progr.owlgame.domain.model.MerchantShopModel
import pro.progr.owlgame.domain.repository.MerchantRepository
import javax.inject.Inject

class MerchantRepositoryImpl @Inject constructor(private val apiService: LootApiService) : MerchantRepository {
    override suspend fun getMerchantShop(): MerchantShopModel? {

        return apiService.getMerchantShop().body()?.toDomain()
    }
}