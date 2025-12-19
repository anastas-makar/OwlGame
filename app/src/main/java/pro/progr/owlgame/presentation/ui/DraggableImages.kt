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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clipToBounds() // визуально не даём рисовать за рамками
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


