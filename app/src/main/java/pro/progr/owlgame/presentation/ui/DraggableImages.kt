package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.data.db.Slot
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
            mapViewModel.selectedBuilding.value?.let {
                DraggableImage(
                    slot = Slot(x = 50f, y = 56f, mapId = map.value.id, buildingId = it.id),
                    mapViewModel = mapViewModel)

            }
        }

        for (slot in map.value.slots) {
            DraggableImage(slot = slot, mapViewModel = mapViewModel)
        }

    }
}

