package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.domain.model.EnemyModel
import pro.progr.owlgame.domain.model.EnemyStatus

@Composable
fun EnemyGalleryCard(
    enemy: EnemyModel,
    onClick: () -> Unit
) {
    val isDefeated = enemy.status == EnemyStatus.DEFEATED
    val isActive = enemy.status == EnemyStatus.ACTIVE

    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable(onClick = onClick),
        elevation = if (isActive) 6.dp else 2.dp,
        backgroundColor = if (isActive) Color(0xFFFFF3E0) else Color.White
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                model = enemy.imageUrl,
                contentDescription = enemy.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .alpha(if (isDefeated) 0.35f else 1f),
                contentScale = ContentScale.Crop,
                colorFilter = if (isDefeated) {
                    ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                } else {
                    null
                }
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = enemy.name,
                style = MaterialTheme.typography.subtitle2
            )

            val statusText = when (enemy.status) {
                EnemyStatus.ACTIVE -> "В бою"
                EnemyStatus.DEFEATED -> "Повержен"
                EnemyStatus.UNTOUCHED -> "Ждёт очереди"
            }

            Text(
                text = statusText,
                style = MaterialTheme.typography.caption
            )

            if (isActive) {
                Spacer(Modifier.height(6.dp))
                Text("Heal: ${enemy.healAmount}")
                Text("Damage: ${enemy.damageAmount}")
            }
        }
    }
}