package pro.progr.owlgame.presentation.ui.craft

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.domain.model.SupplyModel

@Composable
fun SupplyCard(
    supply: SupplyModel,
    onClick: () -> Unit
) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(Modifier.padding(12.dp)) {
            AsyncImage(
                model = supply.imageUrl,
                contentDescription = supply.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = supply.name,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1
            )

            val effectText = supply.effectText()
            if (effectText != null) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = effectText,
                    style = MaterialTheme.typography.caption
                )
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Количество: ${supply.amount}",
                style = MaterialTheme.typography.body2
            )
        }
    }
}