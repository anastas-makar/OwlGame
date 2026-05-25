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
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.domain.model.StreetDirection

@Composable
fun CreateStreetDialog(
    onDismiss: () -> Unit,
    onCreate: (name: String, direction: StreetDirection) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var direction by rememberSaveable { mutableStateOf(StreetDirection.WEST_TO_EAST) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Новая улица") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Название улицы") },
                    singleLine = true
                )

                Spacer(Modifier.height(12.dp))

                Text("Направление улицы")

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = direction == StreetDirection.WEST_TO_EAST,
                        onClick = { direction = StreetDirection.WEST_TO_EAST }
                    )
                    Text("С запада на восток")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = direction == StreetDirection.NORTH_TO_SOUTH,
                        onClick = { direction = StreetDirection.NORTH_TO_SOUTH }
                    )
                    Text("С севера на юг")
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
                Text("Создать")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}