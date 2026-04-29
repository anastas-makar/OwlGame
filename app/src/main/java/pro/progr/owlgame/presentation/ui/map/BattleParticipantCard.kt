package pro.progr.owlgame.presentation.ui.map

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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun BattleParticipantCard(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String?,
    heal: Int?,
    maxHeal: Int?,
    damage: Int?,
    maxDamage: Int?,
    subtitle: String,
    dimmed: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .alpha(if (dimmed) 0.45f else 1f),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(8.dp))

            Text(title, style = MaterialTheme.typography.subtitle1)
            Text(subtitle, style = MaterialTheme.typography.caption)

            if (heal != null && damage != null
                && maxHeal != null && maxDamage != null) {
                Spacer(Modifier.height(8.dp))
                Text("Heal: $heal")
                BattleStatBar(
                    label = "Heal",
                    current = heal,
                    max = maxHeal
                )

                Spacer(modifier = Modifier.height(8.dp))

                BattleStatBar(
                    label = "Damage",
                    current = damage,
                    max = maxDamage
                )
                Text("Damage: $damage")
            }
        }
    }
}