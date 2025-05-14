package pro.progr.owlgame.presentation.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pro.progr.owlgame.R
import pro.progr.owlgame.data.db.BuildingType
import pro.progr.owlgame.data.db.Slot
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import kotlin.math.roundToInt

@Composable
fun NewDraggableImage(slot: Slot,
                      buildingType: BuildingType,
                      mapViewModel: MapViewModel) {
    val houseOffset = remember { mutableStateOf(Offset(slot.x, slot.y)) }

    val scale = remember { Animatable(1f) }
    val isDragged = remember { mutableStateOf(false) }

    val imageHeightDp = 48.dp
    val imageHeightPx = with(LocalDensity.current) { imageHeightDp.toPx() }

    // Анимация мигания, пока не начали таскать
    LaunchedEffect(isDragged.value) {
        while (!isDragged.value) {
            scale.animateTo(
                targetValue = 1.2f,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
            delay(4000) // пауза между миганиями, 4 секунды
        }
    }

    Image(
        painter = painterResource(
            if (buildingType == BuildingType.HOUSE)
                R.drawable.map_icon_house
            else
                R.drawable.map_icon_fortress),
        contentDescription = "Полупрозрачное изображение",
        modifier = Modifier
            .offset {
                IntOffset(
                    houseOffset.value.x.roundToInt(),
                    (houseOffset.value.y - (imageHeightPx * (scale.value - 1f))).roundToInt()
                )
            }
            .graphicsLayer {
                scaleX = scale.value
                scaleY = scale.value
                shadowElevation = 8f
                shape = RoundedCornerShape(8.dp)
                clip = true
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDragged.value = true
                    },
                    onDrag = { _, dragAmount ->
                        houseOffset.value = Offset(
                            x = houseOffset.value.x + dragAmount.x,
                            y = houseOffset.value.y + dragAmount.y
                        )
                    },
                    onDragEnd = {
                        isDragged.value = true
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
                                    y = houseOffset.value.y
                                )
                            )
                        }
                    }
                )
            }
    )
}
