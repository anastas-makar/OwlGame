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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.GardenItemModel
import pro.progr.owlgame.domain.model.SupplyModel

@Composable
fun HarvestGardenItemDialog(
    gardenItem: GardenItemModel,
    supplyFlow: Flow<SupplyModel?>,
    onHarvestSupply: () -> Unit,
    onDismiss: () -> Unit
) {
    val supply = supplyFlow.collectAsState(initial = null).value
    val supplyName = supply?.name ?: stringResource(R.string.default_supply_name)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.harvest_title)) },
        text = {
            Text(stringResource(R.string.harvest_garden_item_message, gardenItem.name))
        },
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
                ) { Text(stringResource(R.string.cancel)) }
            }
        }
    )
}

