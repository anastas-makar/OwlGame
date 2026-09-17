package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.domain.model.StreetDirection
import pro.progr.owlgame.R

@Composable
fun CreateStreetDialog(
    onDismiss: () -> Unit,
    onCreate: (name: String, direction: StreetDirection) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var direction by rememberSaveable { mutableStateOf(StreetDirection.WEST_TO_EAST) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.new_street)) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.street_name)) },
                    singleLine = true
                )

                Spacer(Modifier.height(12.dp))

                Text(stringResource(R.string.street_direction))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = direction == StreetDirection.WEST_TO_EAST,
                        onClick = { direction = StreetDirection.WEST_TO_EAST }
                    )
                    Text(stringResource(R.string.street_west_to_east))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = direction == StreetDirection.NORTH_TO_SOUTH,
                        onClick = { direction = StreetDirection.NORTH_TO_SOUTH }
                    )
                    Text(stringResource(R.string.street_north_to_south))
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = name.isNotBlank(),
                onClick = {
                    onCreate(name.trim(), direction)
                }
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
