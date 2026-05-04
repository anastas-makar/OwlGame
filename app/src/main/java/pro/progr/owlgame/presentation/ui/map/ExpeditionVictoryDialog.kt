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
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel

@Composable
fun ExpeditionVictoryDialog(
    expedition: ExpeditionWithDataModel,
    animal: AnimalModel,
    onExploreClick: () -> Unit
) {
    Dialog(onDismissRequest = {}) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 8.dp,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Победа!", style = MaterialTheme.typography.h6)

                Spacer(Modifier.height(8.dp))

                Text("${animal.kind} ${animal.name} освобождает местность.")
                Text("${animal.kind} ${animal.name} получает медаль!")

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

                    AsyncImage(
                        model = expedition.medal.imageUrl,
                        contentDescription = expedition.medal.title,
                        modifier = Modifier
                            .weight(1f)
                            .height(160.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(expedition.medal.title, style = MaterialTheme.typography.subtitle1)
                Text(expedition.medal.description)

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = onExploreClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Обшарить подземелья")
                }
            }
        }
    }
}