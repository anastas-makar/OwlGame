package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.AnimalStatus
import pro.progr.owlgame.domain.model.BuildingWithDataModel
import pro.progr.owlgame.domain.model.ExpeditionMedalModel
import pro.progr.owlgame.presentation.ui.fab.FabViewModel
import pro.progr.owlgame.presentation.viewmodel.BuildingFacadeViewModel

@Composable
fun BuildingFacade(data: BuildingWithDataModel,
                   fabViewModel: FabViewModel,
                   facadeViewModel: BuildingFacadeViewModel
) {
    fabViewModel.showFab.value = false

    val medals by facadeViewModel.medals.collectAsState()

    Column(Modifier.fillMaxSize()) {
        LargeImage(imageUrl = data.imageUrl)

        Spacer(Modifier.height(12.dp))

        BuildingResidentCard(
            animal = data.animal,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        MedalsRow(
            animal = data.animal,
            medals = medals,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun BuildingResidentCard(
    animal: AnimalModel?,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .then(
                if (animal != null) Modifier.clickable { showDialog = true }
                else Modifier
            ),
        elevation = 4.dp,
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Аватар чуть больше
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(16.dp))
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
                    Text("—", style = MaterialTheme.typography.h4, color = Color.Gray)
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
                        AnimalStatus.GONE -> null // на всякий случай
                    }

                    if (statusLine != null) {
                        Spacer(Modifier.height(4.dp))
                        StatusChip(text = statusLine)
                    }
                }
            }
        }
    }

    if (animal != null && showDialog) {
        AnimalProfileDialog(
            animal = animal,
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
private fun MedalsRow(
    animal: AnimalModel?,
    medals: List<ExpeditionMedalModel>,
    modifier: Modifier = Modifier
) {
    if (animal == null || medals.isEmpty()) return

    var selectedMedal by remember { mutableStateOf<ExpeditionMedalModel?>(null) }

    Column(modifier = modifier.padding(top = 10.dp)) {
        Text(
            text = "${animal.kind.replaceFirstChar { it.uppercase() }} ${animal.name} имеет награды",
            style = MaterialTheme.typography.subtitle2
        )

        Spacer(Modifier.height(6.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = medals,
                key = { it.id }
            ) { medal ->
                MedalSmallCard(
                    medal = medal,
                    onClick = { selectedMedal = medal }
                )
            }
        }
    }

    selectedMedal?.let { medal ->
        MedalDialog(
            medal = medal,
            onDismiss = { selectedMedal = null }
        )
    }
}

@Composable
private fun MedalSmallCard(
    medal: ExpeditionMedalModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable(onClick = onClick),
        elevation = 3.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(Modifier.padding(8.dp)) {
            AsyncImage(
                model = medal.imageUrl,
                contentDescription = medal.title,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = medal.title,
                style = MaterialTheme.typography.caption,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun MedalDialog(
    medal: ExpeditionMedalModel,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                AsyncImage(
                    model = medal.imageUrl,
                    contentDescription = medal.title,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = medal.title,
                    style = MaterialTheme.typography.h6
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = medal.description,
                    style = MaterialTheme.typography.body2
                )
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
