package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pro.progr.owlgame.presentation.ui.model.MapData
import pro.progr.owlgame.presentation.viewmodel.MapViewModel

@Composable
fun DraggableImages(map: State<MapData>, mapViewModel: MapViewModel) {
    var mapSizePx by remember { mutableStateOf(IntSize.Zero) }

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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.clipToBounds() // визуально не даём рисовать за рамками
    ) {
        AsyncImage(
            model = map.value.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { mapSizePx = it }   // <-- ширина/высота карты в px
        )

        if (mapSizePx.width == 0 || mapSizePx.height == 0) return@Box

        for (item in map.value.buildings) {
            key(item.building.id) {
                DraggableBuildingIcon(
                    building = item.building,
                    mapSizePx = mapSizePx,
                    onSave01 = { x01, y01 ->
                        mapViewModel.updateSlot(item.building.id, x01, y01)
                    }
                )
            }
        }
    }
}


