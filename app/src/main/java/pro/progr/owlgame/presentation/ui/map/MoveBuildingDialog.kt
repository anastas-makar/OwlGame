package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.domain.model.BuildingWithAnimalModel
import pro.progr.owlgame.domain.model.StreetWithBuildingsModel

@Composable
fun MoveBuildingDialog(
    building: BuildingWithAnimalModel,
    streets: List<StreetWithBuildingsModel>,
    onDismiss: () -> Unit,
    onMove: (String?) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Перенести дом")
        },
        text = {
            Column {
                Text(
                    text = building.name,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                streets.forEach { street ->
                    TextButton(
                        onClick = { onMove(street.id) },
                        enabled = street.id != building.streetId
                    ) {
                        Text(street.name)
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}