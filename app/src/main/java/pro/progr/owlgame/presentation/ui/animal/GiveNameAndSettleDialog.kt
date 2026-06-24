package pro.progr.owlgame.presentation.ui.animal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalModel

@Composable
fun GiveNameAndSettleDialog(
    animal: AnimalModel?,
    buildingName: String,
    isBusy: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    if (animal == null) return

    var name by remember(animal.id, animal.name) {
        mutableStateOf(animal.nameForEditing())
    }

    AlertDialog(
        onDismissRequest = {
            if (!isBusy) onDismiss()
        },
        title = {
            Text(stringResource(R.string.give_name_dialog_title))
        },
        text = {
            Column {
                Text(stringResource(R.string.give_name_dialog_description))

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = {
                        Text(stringResource(R.string.give_name_dialog_name_label))
                    },
                    singleLine = true,
                    enabled = !isBusy,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(
                        R.string.give_name_dialog_building,
                        buildingName
                    ),
                    style = MaterialTheme.typography.body2
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(name.trim())
                },
                enabled = !isBusy && name.isNotBlank()
            ) {
                Text(stringResource(R.string.give_name_dialog_confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isBusy
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

private fun AnimalModel.nameForEditing(): String {
    return name.orEmpty()
}