package pro.progr.owlgame.presentation.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pro.progr.owlgame.R
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.BuildingType

@Composable
fun DraggableBuildingIcon(
    building: Building,
    mapSizePx: IntSize,
    onSave01: (Float, Float) -> Unit
) {
    val density = LocalDensity.current

    val iconDp = 50.dp
    val iconPx = with(density) { iconDp.toPx() }

    val maxX = (mapSizePx.width.toFloat() - iconPx).coerceAtLeast(0f)
    val maxY = (mapSizePx.height.toFloat() - iconPx).coerceAtLeast(0f)

    fun normToPx(v01: Float, max: Float) = (v01.coerceIn(0f, 1f) * max)
    fun pxToNorm(px: Float, max: Float) = if (max <= 0f) 0f else (px / max).coerceIn(0f, 1f)

    fun clampPx(p: Offset) = Offset(
        x = p.x.coerceIn(0f, maxX),
        y = p.y.coerceIn(0f, maxY)
    )

    // “Новый домик” — лучше делать отдельным флагом/nullable координатами.
    // Если пока никак: считаем новым, когда он ровно (0,0) и ещё не трогали.
    var touched by remember(building.id) { mutableStateOf(false) }
    val isNew = !touched && building.x == 0f && building.y == 0f

    var isDragging by remember(building.id) { mutableStateOf(false) }

    // Локальная позиция в px (для плавного drag)
    var posPx by remember(building.id, maxX, maxY) {
        mutableStateOf(
            Offset(
                normToPx(building.x, maxX),
                normToPx(building.y, maxY)
            )
        )
    }

    // Чтобы домик не “отпрыгивал назад” до прихода обновления из БД:
    var pendingCommit01 by remember(building.id) { mutableStateOf<Offset?>(null) }
    val eps = 0.0005f

    LaunchedEffect(building.id, building.x, building.y, maxX, maxY, isDragging) {
        if (isDragging) return@LaunchedEffect

        val pending = pendingCommit01
        if (pending != null) {
            val ok =
                kotlin.math.abs(building.x - pending.x) < eps &&
                        kotlin.math.abs(building.y - pending.y) < eps
            if (!ok) return@LaunchedEffect // игнорим “старые” координаты из БД
            pendingCommit01 = null
        }

        posPx = Offset(
            normToPx(building.x, maxX),
            normToPx(building.y, maxY)
        )
    }

    // Анимация “прыжка”
    val scale = remember(building.id) { Animatable(1f) }
    LaunchedEffect(building.id, isNew) {
        if (!isNew) return@LaunchedEffect
        while (isNew && !isDragging) {
            scale.animateTo(1.2f, tween(300, easing = FastOutSlowInEasing))
            scale.animateTo(1f, tween(300, easing = FastOutSlowInEasing))
            delay(3000)
        }
    }
    val liftPx = (scale.value - 1f) * iconPx * 1.2f  // можно подкрутить

    Image(
        painter = painterResource(
            if (building.type == BuildingType.HOUSE) R.drawable.map_icon_house
            else R.drawable.map_icon_fortress
        ),
        contentDescription = null,
        modifier = Modifier
            .size(iconDp)
            .graphicsLayer {
                // ВАЖНО: двигаем через translation (быстрее, чем offset)
                translationX = posPx.x
                translationY = (posPx.y - liftPx)
                scaleX = scale.value
                scaleY = scale.value
                clip = true
                shape = RoundedCornerShape(25.dp)
            }
            .pointerInput(building.id, maxX, maxY) {
                detectDragGestures(
                    onDragStart = {
                        touched = true
                        isDragging = true
                    },
                    onDrag = { _, dragAmount ->
                        posPx = clampPx(posPx + dragAmount)
                    },
                    onDragEnd = {
                        isDragging = false

                        val x01 = pxToNorm(posPx.x, maxX)
                        val y01 = pxToNorm(posPx.y, maxY)

                        pendingCommit01 = Offset(x01, y01) // ждём подтверждение из БД
                        onSave01(x01, y01)
                    }
                )
            }
    )
}

