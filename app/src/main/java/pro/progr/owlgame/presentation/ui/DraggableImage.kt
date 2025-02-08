package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.R
import pro.progr.owlgame.data.db.Slot
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import kotlin.math.roundToInt

@Composable
fun DraggableImage(slot: Slot, mapViewModel: MapViewModel) {
    // Состояния для координат первого и второго изображения
    val houseOffset = remember { mutableStateOf(Offset(slot.x, slot.y)) }
    Image(
        painter = painterResource(R.drawable.map_icon_house),
        contentDescription = "Полупрозрачное изображение",
        modifier = Modifier
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
                        if (slot.id == 0) {
                            mapViewModel.newHouseState.value = false

                            mapViewModel.saveSlot(
                                houseOffset.value.x,
                                houseOffset.value.y,
                                slot.mapId,
                                slot.buildingId
                            )
                        } else {
                            mapViewModel.updateSlot(
                                slot.copy(
                                    x = houseOffset.value.x,
                                    y = houseOffset.value.y))
                        }
                    })
            }
            .graphicsLayer {
                shadowElevation = 8f
                shape = RoundedCornerShape(8.dp)
                clip = true
            }
    )
}