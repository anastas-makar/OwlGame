package pro.progr.owlgame.presentation.ui.mapslist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import pro.progr.owlgame.R

@Composable
fun CreateCountryDialog(
    onDismiss: () -> Unit,
    onCreateCountry: (String) -> Unit
) {
    var countryName by rememberSaveable { mutableStateOf("") }
    val trimmedName = countryName.trim()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.create_country))
        },
        text = {
            OutlinedTextField(
                value = countryName,
                onValueChange = { countryName = it },
                label = {
                    Text(stringResource(R.string.country_name))
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onCreateCountry(trimmedName) },
                enabled = trimmedName.isNotBlank()
            ) {
                Text(stringResource(R.string.create))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}