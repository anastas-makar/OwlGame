package pro.progr.owlgame.presentation.ui.merchant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.R

@Composable
fun MerchantEmptyContent(
    modifier: Modifier = Modifier.Companion
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.merchant),
            contentDescription = stringResource(R.string.merchant_shop_merchant_content_description),
            modifier = Modifier.Companion.size(128.dp)
        )

        Text(
            text = stringResource(R.string.merchant_shop_empty),
            style = MaterialTheme.typography.body1
        )
    }
}