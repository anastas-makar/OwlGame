package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
import coil.compose.AsyncImage
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.model.MapData
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import kotlin.math.roundToInt

@Composable
fun DraggableImages(map: State<MapData>, mapViewModel: MapViewModel) {
    // Состояния для координат первого и второго изображения
    val houseOffset = remember { mutableStateOf(Offset(50f, 56f)) }
    val fortressOffset = remember { mutableStateOf(Offset(250f, 56f)) }

    Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
        // Основное изображение на фоне
        AsyncImage(
            model = map.value.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        // Первое изображение (драгаемое)
        if (mapViewModel.newHouseState.value) {
            Image(
                painter = painterResource(R.drawable.map_icon_house),
                contentDescription = "Полупрозрачное изображение",
                modifier = Modifier
                    .offset { IntOffset(houseOffset.value.x.roundToInt(), houseOffset.value.y.roundToInt()) }
                    .pointerInput(Unit) {
                        detectDragGestures (
                            onDrag = { _, dragAmount ->
                            houseOffset.value = Offset(
                                x = houseOffset.value.x + dragAmount.x,
                                y = houseOffset.value.y + dragAmount.y
                            )
                        }, onDragEnd = {
                                mapViewModel.saveSlot(houseOffset.value.x, houseOffset.value.y, map.value.id)
                            })
                    }
                    .graphicsLayer {
                        shadowElevation = 8f
                        shape = RoundedCornerShape(8.dp)
                        clip = true
                    }
            )
        }

        // Второе изображение (драгаемое)
        if (mapViewModel.newFortressState.value) {
            Image(
                painter = painterResource(R.drawable.map_icon_fortress),
                contentDescription = "Полупрозрачное изображение",
                modifier = Modifier
                    .offset { IntOffset(fortressOffset.value.x.roundToInt(), fortressOffset.value.y.roundToInt()) }
                    .pointerInput(Unit) {
                        detectDragGestures { _, dragAmount ->
                            fortressOffset.value = Offset(
                                x = fortressOffset.value.x + dragAmount.x,
                                y = fortressOffset.value.y + dragAmount.y
                            )
                        }
                    }
                    .graphicsLayer {
                        shadowElevation = 8f
                        shape = RoundedCornerShape(8.dp)
                        clip = true
                    }
            )
        }
    }
}