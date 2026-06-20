package pro.progr.owlgame.presentation.ui.mapslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.flowOf
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.AnimalStatus
import pro.progr.owlgame.domain.model.CountryModel
import pro.progr.owlgame.presentation.viewmodel.MapsViewModel

@Composable
fun CountryRulerCard(
    country: CountryModel,
    mapsViewModel: MapsViewModel,
    onAppointRuler: () -> Unit,
    onRemoveRuler: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rulerFlow = remember(country.rulerAnimalId) {
        country.rulerAnimalId?.let { mapsViewModel.observeAnimal(it) }
            ?: flowOf(null)
    }

    val ruler by rulerFlow.collectAsState(initial = null)

    var showRulerDialog by rememberSaveable(country.id, country.rulerAnimalId) {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = 3.dp,
        backgroundColor = Color(0xFFF7F7F7)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (ruler != null) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showRulerDialog = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ruler!!.imagePath,
                        contentDescription = ruler!!.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(58.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Spacer(Modifier.width(10.dp))

                    Column {
                        Text(
                            text = stringResource(R.string.country_ruler),
                            style = MaterialTheme.typography.caption,
                            color = Color.Gray
                        )

                        Text(
                            text = "${ruler!!.kind} ${ruler!!.name}",
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold
                        )

                        AnimalStatusLine(ruler!!.status)
                    }
                }

                TextButton(onClick = onAppointRuler) {
                    Text(stringResource(R.string.change_ruler))
                }
            } else {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.country_ruler),
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray
                    )

                    Text(
                        text = stringResource(R.string.no_country_ruler),
                        style = MaterialTheme.typography.body1
                    )
                }

                TextButton(onClick = onAppointRuler) {
                    Text(stringResource(R.string.appoint_ruler))
                }
            }
        }
    }

    if (showRulerDialog && ruler != null) {
        RulerProfileDialog(
            animal = ruler!!,
            onDismiss = { showRulerDialog = false },
            onDepose = {
                showRulerDialog = false
                onRemoveRuler()
            }
        )
    }
}

@Composable
private fun AnimalStatusLine(status: AnimalStatus) {
    val text = when (status) {
        AnimalStatus.PET -> null
        AnimalStatus.EXPEDITION -> stringResource(R.string.animal_status_expedition)
        AnimalStatus.FUGITIVE -> stringResource(R.string.animal_status_fugitive)
        AnimalStatus.SEARCHING -> stringResource(R.string.animal_status_searching)
        AnimalStatus.GONE -> stringResource(R.string.animal_status_gone)
    }

    if (text != null) {
        Text(
            text = text,
            style = MaterialTheme.typography.caption,
            color = Color.Gray
        )
    }
}