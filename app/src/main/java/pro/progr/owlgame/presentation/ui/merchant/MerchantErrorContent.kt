package pro.progr.owlgame.presentation.ui.merchant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.R

@Composable
fun MerchantErrorContent(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.body1
        )

        OutlinedButton(onClick = onRetry) {
            Text(text = stringResource(R.string.merchant_shop_retry))
        }
    }
}