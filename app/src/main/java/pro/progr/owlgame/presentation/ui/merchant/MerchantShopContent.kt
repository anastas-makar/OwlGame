package pro.progr.owlgame.presentation.ui.merchant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantShopItemUi
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantShopUiState

@Composable
fun MerchantShopContent(
    state: MerchantShopUiState,
    onItemClick: (MerchantShopItemUi) -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            MerchantHeader(
                description = state.description,
                diamonds = state.diamonds,
                currentPrice = state.currentPrice,
                hasEnoughDiamonds = state.hasEnoughDiamonds
            )
        }

        state.sections
            .filter { it.items.isNotEmpty() }
            .forEach { section ->
                item {
                    Text(
                        text = sectionTitle(section.type),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Companion.Bold
                    )
                }

                items(section.items.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier.Companion.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEach { item ->
                            MerchantItemCard(
                                item = item,
                                price = state.currentPrice,
                                canBuy = state.hasEnoughDiamonds && !state.isBuying,
                                onClick = { onItemClick(item) },
                                modifier = Modifier.Companion.weight(1f)
                            )
                        }

                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.Companion.weight(1f))
                        }
                    }
                }
            }
    }
}