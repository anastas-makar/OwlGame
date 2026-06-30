package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.diamondapi.GetDiamondsCountInterface
import pro.progr.owlgame.domain.usecase.BuyMerchantItemUseCase
import pro.progr.owlgame.domain.usecase.GetMerchantShopUseCase
import pro.progr.owlgame.presentation.viewmodel.MerchantShopViewModel
import javax.inject.Inject

class MerchantShopViewModelFactory @Inject constructor(
    private val getMerchantShopUseCase: GetMerchantShopUseCase,
    private val buyMerchantItemUseCase: BuyMerchantItemUseCase,
    private val diamondsProvider: GetDiamondsCountInterface): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MerchantShopViewModel::class.java)) {
            return MerchantShopViewModel(getMerchantShopUseCase,
                buyMerchantItemUseCase,
                diamondsProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}