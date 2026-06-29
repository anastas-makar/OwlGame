package pro.progr.owlgame.presentation.ui.merchant

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantItemKey
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantShopItemUi
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantShopUiState

@Composable
fun MerchantShopScreen(
    state: MerchantShopUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    onBuyConfirmed: (MerchantItemKey) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableStateOf<MerchantShopItemUi?>(null) }

    selectedItem?.let { item ->
        MerchantBuyDialog(
            item = item,
            price = state.currentPrice,
            isBuying = state.isBuying,
            onDismiss = { selectedItem = null },
            onConfirm = {
                selectedItem = null
                onBuyConfirmed(item.key)
            }
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.title
                            ?.takeIf { it.isNotBlank() }
                            ?: stringResource(R.string.merchant_shop_default_title)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.merchant_shop_back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.errorMessage != null -> {
                    MerchantErrorContent(
                        message = state.errorMessage,
                        onRetry = onRetry,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.sections.all { it.items.isEmpty() } -> {
                    MerchantEmptyContent(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    MerchantShopContent(
                        state = state,
                        onItemClick = { item -> selectedItem = item },
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

