package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.domain.usecase.BuyMerchantItemUseCase
import pro.progr.owlgame.domain.usecase.GetMerchantShopUseCase
import pro.progr.owlgame.presentation.viewmodel.MerchantShopViewModel
import javax.inject.Inject
import javax.inject.Named

class MerchantShopViewModelFactory @Inject constructor(
    private val getMerchantShopUseCase: GetMerchantShopUseCase,
    private val buyMerchantItemUseCase: BuyMerchantItemUseCase,
    private val diamondsProvider: PurchaseInterface,
    @Named("baseUrl") private val baseUrl: String): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MerchantShopViewModel::class.java)) {
            return MerchantShopViewModel(getMerchantShopUseCase,
                buyMerchantItemUseCase,
                diamondsProvider,
                baseUrl) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}