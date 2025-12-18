package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.presentation.ui.model.MapData
import pro.progr.owlgame.presentation.viewmodel.MapViewModel

@Composable
fun DraggableImages(map: State<MapData>,
                    mapViewModel: MapViewModel) {

    LaunchedEffect(
        mapViewModel.newHouseState.value,
        mapViewModel.selectedBuilding.value?.id,
        map.value.id
    ) {
        val selected = mapViewModel.selectedBuilding.value
        if (mapViewModel.newHouseState.value && selected != null && map.value.id.isNotEmpty()) {
            mapViewModel.saveSlot(
                x = 0f,
                y = 0f,
                mapId = map.value.id,
                buildingId = selected.id
            )
        }
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()) {
        // Основное изображение на фоне
        AsyncImage(
            model = map.value.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        for (item in map.value.buildings) {
            key(item.building.id) {
                val b = item.building
                DraggableImage(building = b, mapViewModel = mapViewModel)
            }
        }

    }
}

