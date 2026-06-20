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

    val countries = state.countries.map { it.country }

    var fabExpanded by rememberSaveable { mutableStateOf(false) }

    val createCountryText = stringResource(R.string.create_country)

    var countryPendingDeletionId by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val countryPendingDeletion = state.countries.firstOrNull {
        it.country.id == countryPendingDeletionId
    }

    var countryForRulerDialogId by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val countryForRulerDialog = state.countries.firstOrNull {
        it.country.id == countryForRulerDialogId
    }

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
                                navController = navController,
                                countries = countries,
                                onMoveToCountry = mapsViewModel::moveTownToCountry
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
                                    countryPendingDeletionId = section.country.id
                                }
                            )
                        }

                        item(
                            key = "ruler_${section.country.id}",
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            CountryRulerCard(
                                country = section.country,
                                mapsViewModel = mapsViewModel,
                                onAppointRuler = {
                                    countryForRulerDialogId = section.country.id
                                },
                                onRemoveRuler = {
                                    mapsViewModel.removeRuler(section.country.id)
                                }
                            )
                        }

                        items(section.towns, key = { it.id }) { town ->
                            MapCard(
                                map = town,
                                navController = navController,
                                countries = countries,
                                onMoveToCountry = mapsViewModel::moveTownToCountry
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
                                navController = navController,
                                countries = countries,
                                onMoveToCountry = mapsViewModel::moveTownToCountry
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
                                navController = navController,
                                countries = countries,
                                onMoveToCountry = mapsViewModel::moveTownToCountry
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

    countryPendingDeletion?.let { section ->
        DeleteCountryDialog(
            countryName = section.country.name,
            hasTowns = section.towns.isNotEmpty(),
            onDismiss = {
                countryPendingDeletionId = null
            },
            onConfirm = {
                countryPendingDeletionId = null
                mapsViewModel.deleteCountry(section.country.id)
            }
        )
    }

    countryForRulerDialog?.let { section ->
        val candidates by mapsViewModel
            .observeRulerCandidates(section.country.id)
            .collectAsState(initial = emptyList())

        AppointRulerDialog(
            country = section.country,
            candidates = candidates,
            onDismiss = {
                countryForRulerDialogId = null
            },
            onAppoint = { animalId ->
                countryForRulerDialogId = null
                mapsViewModel.appointRuler(
                    countryId = section.country.id,
                    animalId = animalId
                )
            }
        )
    }
}
