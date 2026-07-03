package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.model.MerchantShopModel
import pro.progr.owlgame.domain.repository.MerchantRepository
import pro.progr.owlgame.domain.repository.WidgetRepository
import javax.inject.Inject

class GetMerchantShopUseCase @Inject constructor(
    private val merchantRepository: MerchantRepository,
    private val widgetRepository: WidgetRepository
) {
    suspend operator fun invoke(): MerchantShopModel? {
        val shop = merchantRepository.getMerchantShop()

        if (shop != null) {
            widgetRepository.markMerchantOpened()
        }

        return shop
    }
}