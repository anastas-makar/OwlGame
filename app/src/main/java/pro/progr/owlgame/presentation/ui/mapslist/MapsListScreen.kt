package pro.progr.owlgame.presentation.ui.mapslist

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pro.progr.owlgame.presentation.ui.MapsListBar
import pro.progr.owlgame.presentation.viewmodel.MapsViewModel

@Composable
fun MapsListScreen(
    backToMain: () -> Unit,
    navController: NavHostController,
    mapsViewModel: MapsViewModel
) {
    val state by mapsViewModel.screenState.collectAsState()

    Scaffold(
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                MapsListBar(backToMain)
            }
        },
        content = { innerPadding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .navigationBarsPadding()
                    .fillMaxSize()
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    FreeTownsHeader(
                        onCreateCountry = {
                            //mapsViewModel.showCreateCountryDialog()
                        }
                    )
                }

                items(state.freeTowns, key = { it.id }) { town ->
                    MapCard(
                        map = town,
                        navController = navController
                    )
                }

                state.countries.forEach { section ->
                    item(
                        key = "country_${section.country.id}",
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        CountryHeader(
                            country = section.country,
                            onDeleteCountry = {
                                //mapsViewModel.deleteCountry(section.country.id)
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

                item(span = { GridItemSpan(maxLineSpan) }) {
                    MapsSectionHeader("Здесь можно основать город")
                }

                items(state.freeMaps, key = { it.id }) { map ->
                    MapCard(
                        map = map,
                        navController = navController
                    )
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    MapsSectionHeader("Эти земли оккупированы. Освободите их!")
                }

                items(state.occupiedMaps, key = { it.id }) { map ->
                    MapCard(
                        map = map,
                        navController = navController
                    )
                }
            }
        }
    )
}
