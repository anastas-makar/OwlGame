package pro.progr.owlgame.presentation.ui.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pro.progr.owlgame.presentation.ui.merchant.MerchantShopScreen
import pro.progr.owlgame.presentation.viewmodel.MerchantShopViewModel

@Composable
fun MerchantShopRoute(
    viewModel: MerchantShopViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.ui.collectAsStateWithLifecycle()

    MerchantShopScreen(
        state = state,
        onBack = onBack,
        onRetry = viewModel::loadShop,
        onBuyConfirmed = viewModel::buyItem
    )
}