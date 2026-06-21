package pro.progr.owlgame.presentation.ui.map

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.MapWithDataModel
import pro.progr.owlgame.presentation.ui.mapslist.AnimalStatusLine

@Composable
fun TownMayorCard(
    map: MapWithDataModel,
    onAppointMayor: () -> Unit,
    onRemoveMayor: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mayor = map.findMayorAnimal()

    var showMayorProfile by rememberSaveable(map.id, map.mayorAnimalId) {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 3.dp,
        backgroundColor = Color(0xFFF7F7F7)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (mayor != null) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { showMayorProfile = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = mayor.imagePath,
                        contentDescription = mayor.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(58.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Spacer(Modifier.width(10.dp))

                    Column {
                        Text(
                            text = stringResource(R.string.town_mayor),
                            style = MaterialTheme.typography.caption,
                            color = Color.Gray
                        )

                        Text(
                            text = "${mayor.kind} ${mayor.name}",
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold
                        )

                        AnimalStatusLine(mayor.status)
                    }
                }

                TextButton(onClick = onAppointMayor) {
                    Text(stringResource(R.string.change_mayor))
                }
            } else {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.town_mayor),
                        style = MaterialTheme.typography.caption,
                        color = Color.Gray
                    )

                    Text(
                        text = stringResource(R.string.no_town_mayor),
                        style = MaterialTheme.typography.body1
                    )
                }

                TextButton(onClick = onAppointMayor) {
                    Text(stringResource(R.string.appoint_mayor))
                }
            }
        }
    }

    if (showMayorProfile && mayor != null) {
        MayorProfileDialog(
            animal = mayor,
            onDismiss = {
                showMayorProfile = false
            },
            onRemoveMayor = {
                showMayorProfile = false
                onRemoveMayor()
            }
        )
    }
}