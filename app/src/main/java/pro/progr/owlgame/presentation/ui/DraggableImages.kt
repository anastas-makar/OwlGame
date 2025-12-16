package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
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

        if (mapViewModel.newHouseState.value) {
            mapViewModel.selectedBuilding.value?.let { b ->

                mapViewModel.saveSlot(
                    x = 0f,
                    y = 0f,
                    map.value.id,
                    b.id
                )

            }
        }

        for (item in map.value.buildings) {
            key(item.building.id) {
                val b = item.building
                if (b.x == 0f && b.y == 0f) {
                    NewDraggableImage(building = b, mapViewModel = mapViewModel)
                } else {
                    DraggableImage(building = b, mapViewModel = mapViewModel)
                }
            }
        }

    }
}

