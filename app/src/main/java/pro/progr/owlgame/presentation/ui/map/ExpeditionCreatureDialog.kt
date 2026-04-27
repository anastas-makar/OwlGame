package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import pro.progr.owlgame.domain.model.EnemyStatus
import pro.progr.owlgame.presentation.ui.model.ExpeditionCreatureDetails

@Composable
fun ExpeditionCreatureDialog(
    target: ExpeditionCreatureDetails,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                when (target) {
                    is ExpeditionCreatureDetails.AnimalDetails -> {
                        AsyncImage(
                            model = target.animal.imagePath,
                            contentDescription = target.animal.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(
                            "${target.animal.kind} ${target.animal.name}",
                            style = MaterialTheme.typography.h6
                        )
                        Text("Статус: в экспедиции")
                        Spacer(Modifier.height(8.dp))
                        Text("Heal: ${target.expedition.healAmount}")
                        Text("Damage: ${target.expedition.damageAmount}")
                    }

                    is ExpeditionCreatureDetails.EnemyDetails -> {
                        val enemy = target.enemy

                        AsyncImage(
                            model = enemy.imageUrl,
                            contentDescription = enemy.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .alpha(if (enemy.status == EnemyStatus.DEFEATED) 0.35f else 1f),
                            contentScale = ContentScale.Crop,
                            colorFilter = if (enemy.status == EnemyStatus.DEFEATED) {
                                ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                            } else {
                                null
                            }
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(enemy.name, style = MaterialTheme.typography.h6)

                        val statusText = when (enemy.status) {
                            EnemyStatus.ACTIVE -> "Активный"
                            EnemyStatus.DEFEATED -> "Повержен"
                            EnemyStatus.UNTOUCHED -> "Не тронут"
                        }

                        Text("Статус: $statusText")
                        Spacer(Modifier.height(8.dp))
                        Text(enemy.description)

                        if (enemy.status == EnemyStatus.ACTIVE) {
                            Spacer(Modifier.height(8.dp))
                            Text("Heal: ${enemy.healAmount}")
                            Text("Damage: ${enemy.damageAmount}")
                        }
                    }
                }
            }
        }
    }
}