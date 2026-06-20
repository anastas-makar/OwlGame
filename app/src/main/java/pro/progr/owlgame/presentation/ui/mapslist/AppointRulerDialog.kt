package pro.progr.owlgame.presentation.ui.mapslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.AnimalStatus
import pro.progr.owlgame.domain.model.CountryModel

@Composable
fun AppointRulerDialog(
    country: CountryModel,
    candidates: List<AnimalModel>,
    onDismiss: () -> Unit,
    onAppoint: (animalId: String) -> Unit
) {
    var selectedAnimalId by rememberSaveable(country.id) {
        mutableStateOf<String?>(country.rulerAnimalId)
    }

    val selectedAnimal = candidates.firstOrNull { it.id == selectedAnimalId }
    val canAppointSelected = selectedAnimal?.status == AnimalStatus.PET &&
            selectedAnimal.id != country.rulerAnimalId

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.appoint_country_ruler))
        },
        text = {
            if (candidates.isEmpty()) {
                Text(stringResource(R.string.no_ruler_candidates))
            } else {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 420.dp)
                ) {
                    items(
                        items = candidates,
                        key = { it.id }
                    ) { animal ->
                        RulerCandidateRow(
                            animal = animal,
                            selected = selectedAnimalId == animal.id,
                            enabled = animal.status == AnimalStatus.PET,
                            onClick = {
                                selectedAnimalId = animal.id
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = canAppointSelected,
                onClick = {
                    selectedAnimalId?.let(onAppoint)
                }
            ) {
                Text(stringResource(R.string.appoint))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}

@Composable
private fun RulerCandidateRow(
    animal: AnimalModel,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val alpha = if (enabled) 1f else 0.45f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            enabled = enabled
        )

        AsyncImage(
            model = animal.imagePath,
            contentDescription = animal.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 6.dp)
                .size(52.dp)
                .clip(RoundedCornerShape(10.dp))
                .alpha(alpha)
        )

        Spacer(Modifier.width(10.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "${animal.kind} ${animal.name}",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = if (enabled) Color.Unspecified else Color.Gray
            )

            val statusText = when (animal.status) {
                AnimalStatus.PET -> stringResource(R.string.can_be_ruler)
                AnimalStatus.EXPEDITION -> stringResource(R.string.cannot_appoint_expedition)
                AnimalStatus.FUGITIVE -> stringResource(R.string.cannot_appoint_fugitive)
                AnimalStatus.SEARCHING -> stringResource(R.string.cannot_appoint_unavailable)
                AnimalStatus.GONE -> stringResource(R.string.cannot_appoint_unavailable)
            }

            Text(
                text = statusText,
                style = MaterialTheme.typography.caption,
                color = Color.Gray
            )
        }
    }
}