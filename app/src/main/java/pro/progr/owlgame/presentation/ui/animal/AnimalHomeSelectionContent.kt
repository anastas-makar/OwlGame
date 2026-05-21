package pro.progr.owlgame.presentation.ui.animal

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.BuildingWithAnimalModel
import pro.progr.owlgame.domain.model.MapWithDataModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimalHomeSelectionContent(
    animal: AnimalModel,
    maps: List<MapWithDataModel>,
    isBusy: Boolean,
    onBuildingClick: (BuildingWithAnimalModel, String) -> Unit,
    onSendAwayClick: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            SearchingAnimalHeader(
                animal = animal,
                isBusy = isBusy,
                onSendAwayClick = onSendAwayClick
            )
        }

        item(
            span = { GridItemSpan(maxLineSpan) }
        ) {
            Text(
                text = "Выберите дом:",
                style = MaterialTheme.typography.h6
            )
        }

        maps.forEach { mapData ->
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                CityHeader(mapData = mapData)
            }

            items(
                items = mapData.buildings,
                key = { building -> building.id }
            ) { building ->
                BuildingCard(
                    building = building,
                    enabled = !isBusy,
                    onClick = {
                        onBuildingClick(building, mapData.id)
                    }
                )
            }
        }
    }
}