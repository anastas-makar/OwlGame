package pro.progr.owlgame.presentation.ui.merchant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.R

@Composable
fun MerchantHeader(
    description: String?,
    diamonds: Int,
    currentPrice: Int,
    hasEnoughDiamonds: Boolean,
    modifier: Modifier = Modifier.Companion
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.Companion.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.merchant),
                contentDescription = stringResource(R.string.merchant_shop_merchant_content_description),
                modifier = Modifier.Companion.size(96.dp)
            )

            Column(
                modifier = Modifier.Companion.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = description
                        ?.takeIf { it.isNotBlank() }
                        ?: stringResource(R.string.merchant_shop_default_description),
                    style = MaterialTheme.typography.body1
                )

                Text(
                    text = stringResource(R.string.merchant_shop_price, currentPrice),
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Companion.Bold
                )

                Text(
                    text = stringResource(R.string.merchant_shop_price_hint),
                    style = MaterialTheme.typography.body2
                )

                Text(
                    text = stringResource(R.string.merchant_shop_diamonds, diamonds),
                    style = MaterialTheme.typography.body2
                )

                if (!hasEnoughDiamonds) {
                    Text(
                        text = stringResource(R.string.merchant_shop_not_enough_diamonds),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Companion.Bold
                    )
                }
            }
        }
    }
}