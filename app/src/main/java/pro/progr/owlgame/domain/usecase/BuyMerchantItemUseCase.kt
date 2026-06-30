package pro.progr.owlgame.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.domain.model.PouchItemsModel
import javax.inject.Inject

class BuyMerchantItemUseCase @Inject constructor(
    private val savePouchItemsUseCase: SavePouchItemsUseCase,
    private val diamondDao: PurchaseInterface
) {
    suspend operator fun invoke(
        item: PouchItemsModel,
        price: Int
    ): PouchItemsModel {
        val savedItem = savePouchItemsUseCase(item)

        withContext(Dispatchers.IO) {
            diamondDao.spendDiamonds(price)
        }

        return savedItem
    }
}