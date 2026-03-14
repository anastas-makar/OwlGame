package pro.progr.owlgame.presentation.ui.mapicon

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay

@Composable
fun FixedIcon(
    x01: Float,
    y01: Float,
    mapSizePx: IntSize,
    iconPainter: Painter,
    itemSize: Dp,
    isJumping: Boolean
) {
    val density = LocalDensity.current
    val iconPx = with(density) { itemSize.toPx() }

    val maxX = (mapSizePx.width.toFloat() - iconPx).coerceAtLeast(0f)
    val maxY = (mapSizePx.height.toFloat() - iconPx).coerceAtLeast(0f)

    fun normToPx(v01: Float, max: Float) = (v01.coerceIn(0f, 1f) * max)

    // Локальная позиция в px для плавного drag
    var posPx by remember(maxX, maxY) {
        mutableStateOf(Offset(normToPx(x01, maxX), normToPx(y01, maxY)))
    }

    // Анимация “прыжка”
    val scale = remember { Animatable(1f) }
    LaunchedEffect(isJumping) {
        if (!isJumping) return@LaunchedEffect
        while (isJumping) {
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
    )
}