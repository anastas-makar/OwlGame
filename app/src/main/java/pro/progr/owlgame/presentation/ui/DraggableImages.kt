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

                val slot = Slot(x = 0f, y = 0f, mapId = map.value.id, buildingId = it.id)
                mapViewModel.saveSlot(
                    slot.x,
                    slot.y,
                    slot.mapId,
                    slot.buildingId
                )

            }
        }

        for (slot in map.value.slots) {
            if (slot.slot.x == 0f && slot.slot.y == 0f) {
                NewDraggableImage(
                    slot = slot.slot,
                    mapViewModel = mapViewModel)
            } else {
                DraggableImage(slot = slot.slot, mapViewModel = mapViewModel)

            }
        }

    }
}

