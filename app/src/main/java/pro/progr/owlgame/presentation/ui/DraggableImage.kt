package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.R
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.BuildingType
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import kotlin.math.roundToInt

@Composable
fun DraggableImage(building: Building,
                   mapViewModel: MapViewModel) {
    // Состояния для координат первого и второго изображения
    val houseOffset = remember { mutableStateOf(Offset(building.x, building.y)) }
    Image(
        painter = painterResource(
            if (building.type == BuildingType.HOUSE)
                R.drawable.map_icon_house
            else
                R.drawable.map_icon_fortress
        ),
        contentDescription = "Полупрозрачное изображение",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(50.dp)
            .offset {
                IntOffset(
                    houseOffset.value.x.roundToInt(),
                    houseOffset.value.y.roundToInt()
                )
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { _, dragAmount ->
                        houseOffset.value = Offset(
                            x = houseOffset.value.x + dragAmount.x,
                            y = houseOffset.value.y + dragAmount.y
                        )
                    }, onDragEnd = {
                        mapViewModel.newHouseState.value = false

                        mapViewModel.updateSlot(
                            buildingId = building.id,
                            x = houseOffset.value.x,
                            y = houseOffset.value.y
                        )
                    })
            }
            .graphicsLayer {
                shadowElevation = 0f
                shape = RoundedCornerShape(25.dp)
                clip = true
            }
    )
}