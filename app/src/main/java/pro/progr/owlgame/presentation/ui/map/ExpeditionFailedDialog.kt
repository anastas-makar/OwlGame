package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.EnemyModel
import pro.progr.owlgame.presentation.ui.model.ExpeditionFailureReason

@Composable
fun ExpeditionFailedDialog(
    reason: ExpeditionFailureReason,
    animal: AnimalModel,
    enemy: EnemyModel?,
    isLoading: Boolean,
    onRegroupClick: () -> Unit
) {
    Dialog(onDismissRequest = {}) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 8.dp,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = when (reason) {
                        ExpeditionFailureReason.ESCAPE -> "Бегство!"
                        ExpeditionFailureReason.DEFEAT -> "Поражение!"
                    },
                    style = MaterialTheme.typography.h6
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = when (reason) {
                        ExpeditionFailureReason.ESCAPE ->
                            "Вы помогли ${animal.kind} ${animal.name} бежать."
                        ExpeditionFailureReason.DEFEAT ->
                            "${animal.kind} ${animal.name} терпит поражение и скрывается."
                    }
                )

                Spacer(Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AsyncImage(
                        model = animal.imagePath,
                        contentDescription = animal.name,
                        modifier = Modifier
                            .weight(1f)
                            .height(160.dp),
                        contentScale = ContentScale.Crop
                    )

                    if (enemy != null) {
                        AsyncImage(
                            model = enemy.imageUrl,
                            contentDescription = enemy.name,
                            modifier = Modifier
                                .weight(1f)
                                .height(160.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Spacer(Modifier.weight(1f))
                    }
                }

                if (enemy != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Победитель: ${enemy.name}",
                        style = MaterialTheme.typography.subtitle2
                    )
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = onRegroupClick,
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isLoading) "Враги перегруппировываются…" else "Враги перегруппировались")
                }
            }
        }
    }
}