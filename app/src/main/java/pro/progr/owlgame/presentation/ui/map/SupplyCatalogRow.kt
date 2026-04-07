package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.data.db.model.EffectType
import pro.progr.owlgame.presentation.ui.model.SupplySelectionUi

@Composable
fun SupplyCatalogRow(
    item: SupplySelectionUi,
    onMinus: () -> Unit,
    onPlus: () -> Unit
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.supply.imageUrl,
                contentDescription = "Изображение ${item.supply.name}",
                modifier = Modifier
                    .size(64.dp)
                        .background(Color.Transparent, RoundedCornerShape(8.dp))
            )

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.supply.name, style = MaterialTheme.typography.subtitle1)
                Text(item.supply.description, style = MaterialTheme.typography.body2)

                val effectText = when (item.supply.effectType) {
                    EffectType.HEAL -> "Heal +${item.supply.effectAmount}"
                    EffectType.DAMAGE -> "Damage +${item.supply.effectAmount}"
                    EffectType.NO_EFFECT -> "Без эффекта"
                }

                Text(effectText, style = MaterialTheme.typography.caption)
                Text("В наличии: ${item.supply.amount}", style = MaterialTheme.typography.caption)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onMinus,
                    enabled = item.selectedAmount > 0
                ) {
                    Text("-")
                }

                Text(
                    text = item.selectedAmount.toString(),
                    modifier = Modifier.width(24.dp),
                    textAlign = TextAlign.Center
                )

                IconButton(
                    onClick = onPlus,
                    enabled = item.selectedAmount < item.supply.amount
                ) {
                    Text("+")
                }
            }
        }
    }
}