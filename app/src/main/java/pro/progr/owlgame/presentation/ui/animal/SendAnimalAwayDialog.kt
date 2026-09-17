package pro.progr.owlgame.presentation.ui.animal

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pro.progr.owlgame.R

@Composable
fun SendAnimalAwayDialog(
    isBusy: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            if (!isBusy) onDismiss()
        },
        title = {
            Text(stringResource(R.string.send_animal_away_title))
        },
        text = {
            Text(stringResource(R.string.send_animal_away_message))
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = !isBusy
            ) {
                Text(stringResource(R.string.send_animal_away_short))
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
