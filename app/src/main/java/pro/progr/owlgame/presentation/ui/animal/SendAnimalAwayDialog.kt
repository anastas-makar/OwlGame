package pro.progr.owlgame.presentation.ui.animal

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

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
            Text("Прогнать животное?")
        },
        text = {
            Text("Животное уйдёт и больше не будет искать дом.")
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                enabled = !isBusy
            ) {
                Text("Прогнать")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isBusy
            ) {
                Text("Отмена")
            }
        }
    )
}