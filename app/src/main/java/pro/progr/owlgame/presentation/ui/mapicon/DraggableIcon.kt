package pro.progr.owlgame.presentation.ui.mapicon

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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay

@Composable
fun DraggableIcon(
    x01: Float,
    y01: Float,
    mapSizePx: IntSize,
    iconPainter: Painter,
    itemSize: Dp,
    isNew: Boolean,
    onCommit01: (Float, Float) -> Unit,
    onTouched: () -> Unit,
) {
    val density = LocalDensity.current
    val iconPx = with(density) { itemSize.toPx() }

    val maxX = (mapSizePx.width.toFloat() - iconPx).coerceAtLeast(0f)
    val maxY = (mapSizePx.height.toFloat() - iconPx).coerceAtLeast(0f)

    fun normToPx(v01: Float, max: Float) = (v01.coerceIn(0f, 1f) * max)
    fun pxToNorm(px: Float, max: Float) = if (max <= 0f) 0f else (px / max).coerceIn(0f, 1f)

    fun clampPx(p: Offset) = Offset(
        x = p.x.coerceIn(0f, maxX),
        y = p.y.coerceIn(0f, maxY)
    )

    var touched by remember { mutableStateOf(false) }
    var isDragging by remember { mutableStateOf(false) }

    // Локальная позиция в px для плавного drag
    var posPx by remember(maxX, maxY) {
        mutableStateOf(Offset(normToPx(x01, maxX), normToPx(y01, maxY)))
    }

    // Защита от “отскока назад” до прихода подтверждения из БД
    var pendingCommit01 by remember { mutableStateOf<Offset?>(null) }
    val eps = 0.0005f

    LaunchedEffect(x01, y01, maxX, maxY, isDragging) {
        if (isDragging) return@LaunchedEffect

        val pending = pendingCommit01
        if (pending != null) {
            val ok = kotlin.math.abs(x01 - pending.x) < eps && kotlin.math.abs(y01 - pending.y) < eps
            if (!ok) return@LaunchedEffect
            pendingCommit01 = null
        }

        posPx = Offset(normToPx(x01, maxX), normToPx(y01, maxY))
    }

    // Анимация “прыжка” для нового элемента (пока не тронули)
    val scale = remember { Animatable(1f) }
    LaunchedEffect(isNew, touched, isDragging) {
        if (!isNew || touched) return@LaunchedEffect
        while (isNew && !touched && !isDragging) {
            scale.animateTo(1.2f, tween(300, easing = FastOutSlowInEasing))
            scale.animateTo(1f, tween(300, easing = FastOutSlowInEasing))
            delay(3000)
        }
    }
    val liftPx = (scale.value - 1f) * iconPx * 1.2f

    Image(
        painter = iconPainter,
        contentDescription = null,
        modifier = Modifier
            .size(itemSize)
            .graphicsLayer {
                translationX = posPx.x
                translationY = (posPx.y - liftPx)
                scaleX = scale.value
                scaleY = scale.value
                clip = true
                shape = RoundedCornerShape(percent = 50)
            }
            .pointerInput(maxX, maxY) {
                detectDragGestures(
                    onDragStart = {
                        touched = true
                        isDragging = true
                        onTouched()
                    },
                    onDrag = { _, dragAmount ->
                        posPx = clampPx(posPx + dragAmount)
                    },
                    onDragEnd = {
                        isDragging = false
                        val x = pxToNorm(posPx.x, maxX)
                        val y = pxToNorm(posPx.y, maxY)
                        pendingCommit01 = Offset(x, y)
                        onCommit01(x, y)
                    },
                    onDragCancel = { isDragging = false }
                )
            }
    )
}