package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.model.MerchantShopModel
import pro.progr.owlgame.domain.repository.MerchantRepository
import javax.inject.Inject

class GetMerchantShopUseCase @Inject constructor(
    private val merchantRepository: MerchantRepository
) {
    suspend operator fun invoke(): MerchantShopModel? {
        return merchantRepository.getMerchantShop()
    }
}