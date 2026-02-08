package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.GardenItem
import pro.progr.owlgame.data.db.Supply

@Composable
fun HarvestGardenItemDialog(
    gardenItem: GardenItem,
    supplyFlow: Flow<Supply?>,
    onHarvestSupply: () -> Unit,
    onDismiss: () -> Unit
) {
    val supply = supplyFlow.collectAsState(initial = null).value

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Собрать урожай") },
        text = {
            Column {
                Text("После сбора урожая ${gardenItem.name} останется")
                Spacer(Modifier.height(10.dp))

                // Припас
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val supplyName = supply?.name ?: "припас"
                    Text("$supplyName: ${gardenItem.supplyAmount}")
                }
            }
        },
        buttons = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                OutlinedButton(
                    onClick = onHarvestSupply,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = gardenItem.supplyAmount > 0
                ) {
                    val supplyName = supply?.name ?: "припасы"
                    Text("Собрать $supplyName (+${gardenItem.supplyAmount})")
                }

                Spacer(Modifier.height(4.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Отмена")
                }
            }
        }
    )
}
