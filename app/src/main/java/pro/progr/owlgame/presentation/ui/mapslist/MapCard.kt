package pro.progr.owlgame.presentation.ui.mapslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.CountryModel
import pro.progr.owlgame.domain.model.MapModel
import pro.progr.owlgame.domain.model.MapType

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapCard(
    map: MapModel,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    countries: List<CountryModel> = emptyList(),
    onMoveToCountry: ((mapId: String, countryId: String?) -> Unit)? = null
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var moveDialogVisible by remember { mutableStateOf(false) }

    val isTown = map.type == MapType.TOWN
    val canMove = isTown &&
            (countries.isNotEmpty() || map.countryId != null)

    val openMap = {
        navController.navigate("map/${map.id}")
    }

    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column {
            Column(
                modifier = Modifier.clickable(onClick = openMap)
            ) {
                AsyncImage(
                    model = map.imageUrl,
                    contentDescription = map.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )

                Text(
                    text = map.name,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(34.dp)
                    .background(MaterialTheme.colors.surface)
                    .padding(horizontal = 4.dp)
            ) {
                IconButton(
                    onClick = { menuExpanded = true },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(30.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_menu),
                        contentDescription = stringResource(R.string.town_menu),
                        tint = MaterialTheme.colors.onSurface.copy(alpha = 0.55f),
                        modifier = Modifier.size(20.dp)
                    )
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    if (canMove && onMoveToCountry != null) {
                        DropdownMenuItem(
                            onClick = {
                                menuExpanded = false
                                moveDialogVisible = true
                            }
                        ) {
                            Text(stringResource(R.string.move_to_country))
                        }
                    }

                    DropdownMenuItem(
                        onClick = {
                            menuExpanded = false
                            openMap()
                        }
                    ) {
                        Text(stringResource(R.string.enter))
                    }
                }
            }
        }
    }

    if (moveDialogVisible && onMoveToCountry != null) {
        MoveTownToCountryDialog(
            town = map,
            countries = countries,
            onDismiss = { moveDialogVisible = false },
            onMove = { countryId ->
                moveDialogVisible = false
                onMoveToCountry(map.id, countryId)
            }
        )
    }
}