package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
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
    val supplyName = supply?.name ?: "Припас"

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Собрать урожай") },
        text = { Text("После сбора урожая ${gardenItem.name} останется") },
        buttons = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                HarvestOptionButton(
                    imageUrl = supply?.imageUrl,
                    title = supplyName,
                    subtitle = gardenItem.name,
                    amountText = "+${gardenItem.supplyAmount}",
                    onClick = onHarvestSupply,
                    enabled = gardenItem.supplyAmount > 0,
                    outlined = false
                )

                Spacer(Modifier.height(6.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) { Text("Отмена") }
            }
        }
    )
}

