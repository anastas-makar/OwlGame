package pro.progr.owlgame.presentation.ui.map

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.AnimalStatus
import pro.progr.owlgame.domain.model.MapWithDataModel
import pro.progr.owlgame.presentation.ui.mapslist.AnimalCandidateRow

@Composable
fun AppointMayorDialog(
    map: MapWithDataModel,
    candidates: List<AnimalModel>,
    onDismiss: () -> Unit,
    onAppoint: (animalId: String) -> Unit
) {
    var selectedAnimalId by rememberSaveable(map.id) {
        mutableStateOf(map.mayorAnimalId)
    }

    val selectedAnimal = candidates.firstOrNull { it.id == selectedAnimalId }

    val canAppointSelected =
        selectedAnimal != null &&
                selectedAnimal.status == AnimalStatus.PET &&
                selectedAnimal.id != map.mayorAnimalId

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.appoint_town_mayor))
        },
        text = {
            if (candidates.isEmpty()) {
                Text(stringResource(R.string.no_mayor_candidates))
            } else {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 420.dp)
                ) {
                    items(
                        items = candidates,
                        key = { it.id }
                    ) { animal ->
                        AnimalCandidateRow(
                            animal = animal,
                            selected = selectedAnimalId == animal.id,
                            enabled = animal.status == AnimalStatus.PET,
                            availableText = stringResource(R.string.can_be_mayor),
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