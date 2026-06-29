package pro.progr.owlgame.presentation.ui.merchant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantShopItemUi

@Composable
fun MerchantItemCard(
    item: MerchantShopItemUi,
    price: Int,
    canBuy: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 3.dp
    ) {
        Column(
            modifier = Modifier.Companion.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .aspectRatio(1.25f),
                contentScale = ContentScale.Companion.Fit
            )

            Text(
                text = item.title,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Companion.Bold
            )

            item.description
                ?.takeIf { it.isNotBlank() }
                ?.let { description ->
                    Text(
                        text = description,
                        style = MaterialTheme.typography.body2
                    )
                }

            item.extraInfo?.let { extra ->
                Text(
                    text = extraInfoText(extra),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Companion.Medium
                )
            }

            Spacer(modifier = Modifier.Companion.height(4.dp))

            Button(
                onClick = onClick,
                enabled = canBuy,
                modifier = Modifier.Companion.fillMaxWidth()
            ) {
                Text(
                    text = if (canBuy) {
                        stringResource(R.string.merchant_shop_buy_for, price)
                    } else {
                        stringResource(R.string.merchant_shop_not_enough_short)
                    }
                )
            }
        }
    }
}