package pro.progr.owlgame.presentation.ui.merchant

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantShopItemUi

@Composable
fun MerchantBuyDialog(
    item: MerchantShopItemUi,
    price: Int,
    isBuying: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            if (!isBuying) onDismiss()
        },
        title = {
            Text(text = stringResource(R.string.merchant_shop_confirm_title))
        },
        text = {
            Text(
                text = stringResource(
                    R.string.merchant_shop_confirm_message,
                    item.title,
                    price
                )
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = !isBuying
            ) {
                Text(text = stringResource(R.string.merchant_shop_buy))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isBuying
            ) {
                Text(text = stringResource(R.string.merchant_shop_cancel))
            }
        }
    )
}