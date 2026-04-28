package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.domain.model.EnemyModel

@Composable
fun EnemyGalleryRow(
    enemies: List<EnemyModel>,
    onEnemyClick: (EnemyModel) -> Unit
) {
    if (enemies.isEmpty()) return

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(enemies.size, key = { index -> enemies[index].id }) { index ->
            val enemy = enemies[index]
            EnemyGalleryCard(
                enemy = enemy,
                onClick = { onEnemyClick(enemy) }
            )
        }
    }
}