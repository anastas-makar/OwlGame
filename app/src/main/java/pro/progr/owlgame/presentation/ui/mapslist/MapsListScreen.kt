package pro.progr.owlgame.presentation.ui.mapslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.MapsListBar
import pro.progr.owlgame.presentation.ui.fab.ExpandableFloatingActionButton
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.viewmodel.MapsViewModel

@Composable
fun MapsListScreen(
    backToMain: () -> Unit,
    navController: NavHostController,
    mapsViewModel: MapsViewModel
) {
    val state by mapsViewModel.screenState.collectAsState()

    var fabExpanded by rememberSaveable { mutableStateOf(false) }

    val createCountryText = stringResource(R.string.create_country)

    Scaffold(
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                MapsListBar(backToMain)
            }
        },
        floatingActionButton = {
            ExpandableFloatingActionButton(
                expanded = fabExpanded,
                onExpandedChange = { fabExpanded = it },
                actions = listOf(
                    FabAction(
                        text = createCountryText,
                        color = Color.DarkGray,
                        onClick = {
                            fabExpanded = false
                            mapsViewModel.openCreateCountryDialog()
                        }
                    )
                ),
                modifier = Modifier.navigationBarsPadding()
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .navigationBarsPadding()
                    .fillMaxSize()
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (state.freeTowns.isNotEmpty()) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            FreeTownsHeader()
                        }

                        items(state.freeTowns, key = { it.id }) { town ->
                            MapCard(
                                map = town,
                                navController = navController
                            )
                        }
                    }

                    state.countries.forEach { section ->
                        item(
                            key = "country_${section.country.id}",
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            CountryHeader(
                                country = section.country,
                                onDeleteCountry = {
                                    // mapsViewModel.deleteCountry(section.country.id)
                                }
                            )
                        }

                        items(section.towns, key = { it.id }) { town ->
                            MapCard(
                                map = town,
                                navController = navController
                            )
                        }
                    }

                    if (state.freeMaps.isNotEmpty()) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            MapsSectionHeader(R.string.maps_found_town_section)
                        }

                        items(state.freeMaps, key = { it.id }) { map ->
                            MapCard(
                                map = map,
                                navController = navController
                            )
                        }
                    }

                    if (state.occupiedMaps.isNotEmpty()) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            MapsSectionHeader(R.string.maps_occupied_section)
                        }

                        items(state.occupiedMaps, key = { it.id }) { map ->
                            MapCard(
                                map = map,
                                navController = navController
                            )
                        }
                    }
                }

                if (fabExpanded) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.35f))
                            .clickable { fabExpanded = false }
                    )
                }
            }

            if (mapsViewModel.showCreateCountryDialog.value) {
                CreateCountryDialog(
                    onDismiss = {
                        mapsViewModel.closeCreateCountryDialog()
                    },
                    onCreateCountry = { name ->
                        mapsViewModel.createCountry(name)
                    }
                )
            }
        }
    )
}
