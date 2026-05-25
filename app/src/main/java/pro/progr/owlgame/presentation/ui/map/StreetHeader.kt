package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.domain.model.StreetWithBuildingsModel

@Composable
fun StreetHeader(
    street: StreetWithBuildingsModel,
    onDeleteStreet: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var showInfo by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = street.name,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold
        )

        if (street.isMain) {
            IconButton(onClick = { showInfo = true }) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Что это за улица"
                )
            }
        } else {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Меню улицы"
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        menuExpanded = false
                        showDeleteConfirm = true
                    }
                ) {
                    Text("Удалить улицу")
                }
            }
        }
    }

    if (showInfo) {
        AlertDialog(
            onDismissRequest = { showInfo = false },
            title = { Text("Улица Главная") },
            text = {
                Text("Улица Главная — временная улица для домов без выбранной улицы. Она исчезнет, когда вы перенесёте отсюда все дома.")
            },
            confirmButton = {
                TextButton(onClick = { showInfo = false }) {
                    Text("Понятно")
                }
            }
        )
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Удалить улицу?") },
            text = {
                val message = if (street.buildings.isEmpty()) {
                    "Удалить улицу «${street.name}»?"
                } else {
                    "Удалить улицу «${street.name}»? Дома с этой улицы будут перенесены на Улицу Главную."
                }

                Text(message)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteConfirm = false
                        onDeleteStreet()
                    }
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}