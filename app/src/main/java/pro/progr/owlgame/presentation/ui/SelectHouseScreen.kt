package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.presentation.ui.model.BuildingModel
import pro.progr.owlgame.presentation.viewmodel.MapViewModel

@Composable
fun SelectHouseScreen(mapViewModel: MapViewModel, diamondBalance : State<Int>) {
    val buildingsState = mapViewModel.getAvailableHouses().collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(alpha = 0.5f))
            .clickable {
                mapViewModel.selectHouseState.value = false
            }
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(buildingsState.value) { _, building ->

                Box(
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxSize()
                ) {
                    AsyncImage(
                        model = building.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                mapViewModel.selectHouseState.value = false
                                mapViewModel.selectedBuilding.value = BuildingModel(
                                    building.id,
                                    building.name,
                                    building.imageUrl
                                )
                                mapViewModel.newHouseState.value = true
                            },
                        colorFilter = if (diamondBalance.value < building.price) ColorFilter.tint(Color.Gray) else null
                    )
                }
            }
        }

    }
}