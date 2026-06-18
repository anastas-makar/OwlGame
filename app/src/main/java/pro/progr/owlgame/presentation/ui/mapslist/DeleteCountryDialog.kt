package pro.progr.owlgame.presentation.ui.mapslist

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pro.progr.owlgame.R

@Composable
fun DeleteCountryDialog(
    countryName: String,
    hasTowns: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.delete_country_title))
        },
        text = {
            Text(
                text = if (hasTowns) {
                    stringResource(
                        R.string.delete_country_with_towns_message,
                        countryName
                    )
                } else {
                    stringResource(
                        R.string.delete_empty_country_message,
                        countryName
                    )
                }
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = stringResource(R.string.delete),
                    color = MaterialTheme.colors.error
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}