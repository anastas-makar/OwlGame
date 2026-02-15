package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.db.AnimalStatus
import pro.progr.owlgame.data.db.BuildingWithData
import pro.progr.owlgame.presentation.ui.fab.FabViewModel

@Composable
fun BuildingFacade(data: BuildingWithData, fabViewModel: FabViewModel) {
    fabViewModel.showFab.value = false

    Column(Modifier.fillMaxSize()) {
        LargeImage(imageUrl = data.building.imageUrl)

        Spacer(Modifier.height(12.dp))

        BuildingResidentCard(
            animal = data.animal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun BuildingResidentCard(
    animal: Animal?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Аватар
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.Black.copy(alpha = 0.06f)),
                contentAlignment = Alignment.Center
            ) {
                if (animal != null) {
                    AsyncImage(
                        model = animal.imagePath,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("—", style = MaterialTheme.typography.h5, color = Color.Gray)
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                if (animal == null) {
                    Text("Здесь никто не живёт", style = MaterialTheme.typography.body1)
                    Text(
                        "Можно поселить животное позже",
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray
                    )
                } else {
                    Text(
                        text = "Здесь живёт ${animal.kind} ${animal.name}",
                        style = MaterialTheme.typography.body1
                    )

                    val statusLine: String? = when (animal.status) {
                        AnimalStatus.EXPEDITION -> "Сейчас в экспедиции"
                        AnimalStatus.FUGITIVE -> "Временно в бегах"
                        AnimalStatus.PET -> null
                        AnimalStatus.SEARCHING -> null // на всякий случай
                    }

                    if (statusLine != null) {
                        Spacer(Modifier.height(2.dp))
                        StatusChip(
                            text = statusLine,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusChip(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(999.dp))
            .background(Color.Black.copy(alpha = 0.08f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.caption,
            color = Color.DarkGray
        )
    }
}
