package pro.progr.owlgame.presentation.ui.building

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.layout.ContentScale

@Composable
fun <T> DraggableSizedImageOverlay(
    backgroundModel: Any?,
    items: List<T>,
    modifier: Modifier = Modifier,
    keyOf: (T) -> Any,
    x01Of: (T) -> Float,
    y01Of: (T) -> Float,
    width01Of: (T) -> Float,
    height01Of: (T) -> Float,
    itemImageModelOf: (T) -> Any?,
    // если хочешь “подпрыгивание нового” — просто передай newId извне
    isNewOf: (T) -> Boolean = { false },
    // дефолтный размер, если в базе width/height = 0
    defaultWidth01: Float = 0.25f,
    defaultHeight01: Float = 0.35f,
    onCommit01: (T, Float, Float) -> Unit,
) {
    var bgSizePx by remember { mutableStateOf(IntSize.Zero) }

    Box(modifier = modifier.fillMaxWidth()) {

        AsyncImage(
            model = backgroundModel,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { bgSizePx = it }
        )

        if (bgSizePx.width == 0 || bgSizePx.height == 0) return@Box

        @Composable
        fun drawOne(item: T) {
            DraggableSizedImageItem(
                key = keyOf(item),
                bgSizePx = bgSizePx,
                x01 = x01Of(item),
                y01 = y01Of(item),
                width01 = width01Of(item),
                height01 = height01Of(item),
                defaultWidth01 = defaultWidth01,
                defaultHeight01 = defaultHeight01,
                imageModel = itemImageModelOf(item),
                isNew = isNewOf(item),
                onCommit01 = { x, y -> onCommit01(item, x, y) }
            )
        }

        items.forEach { drawOne(it) }
    }
}

@Composable
private fun DraggableSizedImageItem(
    key: Any,
    bgSizePx: IntSize,
    x01: Float,
    y01: Float,
    width01: Float,
    height01: Float,
    defaultWidth01: Float,
    defaultHeight01: Float,
    imageModel: Any?,
    isNew: Boolean,
    onCommit01: (Float, Float) -> Unit,
) {
    val density = LocalDensity.current

    // Нормализация: храним x/y как доли от РАЗМЕРА ФОНА (а не от maxX/maxY)
    val bgW = bgSizePx.width.toFloat().coerceAtLeast(1f)
    val bgH = bgSizePx.height.toFloat().coerceAtLeast(1f)

    val w01 = (if (width01 > 0f) width01 else defaultWidth01).coerceIn(0.01f, 1f)
    val h01 = (if (height01 > 0f) height01 else defaultHeight01).coerceIn(0.01f, 1f)

    val wPx = (w01 * bgW).coerceAtLeast(1f)
    val hPx = (h01 * bgH).coerceAtLeast(1f)

    val maxX = (bgW - wPx).coerceAtLeast(0f)
    val maxY = (bgH - hPx).coerceAtLeast(0f)

    fun clampPx(p: Offset) = Offset(
        x = p.x.coerceIn(0f, maxX),
        y = p.y.coerceIn(0f, maxY)
    )

    // локальная позиция в px
    var posPx by remember(key, maxX, maxY) {
        mutableStateOf(
            clampPx(Offset(x01.coerceIn(0f, 1f) * bgW, y01.coerceIn(0f, 1f) * bgH))
        )
    }

    var touched by remember(key) { mutableStateOf(false) }
    var isDragging by remember(key) { mutableStateOf(false) }

    // анти-отскок до прихода подтверждения из БД
    var pendingCommit01 by remember(key) { mutableStateOf<Offset?>(null) }
    val eps = 0.0005f

    LaunchedEffect(key, x01, y01, bgW, bgH, wPx, hPx, isDragging) {
        if (isDragging) return@LaunchedEffect

        val pending = pendingCommit01
        if (pending != null) {
            val ok = kotlin.math.abs(x01 - pending.x) < eps && kotlin.math.abs(y01 - pending.y) < eps
            if (!ok) return@LaunchedEffect
            pendingCommit01 = null
        }

        posPx = clampPx(Offset(x01.coerceIn(0f, 1f) * bgW, y01.coerceIn(0f, 1f) * bgH))
    }

    // подпрыгивание нового (пока не тронули)
    val scale = remember(key) { Animatable(1f) }
    LaunchedEffect(key, isNew, touched, isDragging) {
        if (!isNew || touched) return@LaunchedEffect
        while (isNew && !touched && !isDragging) {
            scale.animateTo(1.08f, tween(260, easing = FastOutSlowInEasing))
            scale.animateTo(1f, tween(260, easing = FastOutSlowInEasing))
            delay(2800)
        }
    }
    val liftPx = (scale.value - 1f) * hPx * 0.15f

    val wDp = with(density) { wPx.toDp() }
    val hDp = with(density) { hPx.toDp() }

    Box(
        modifier = Modifier
            .size(wDp, hDp)
            .graphicsLayer {
                translationX = posPx.x
                translationY = posPx.y - liftPx
                scaleX = scale.value
                scaleY = scale.value
            }
            .pointerInput(key, maxX, maxY) {
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

                        val xNew01 = (posPx.x / bgW).coerceIn(0f, 1f)
                        val yNew01 = (posPx.y / bgH).coerceIn(0f, 1f)

                        pendingCommit01 = Offset(xNew01, yNew01)
                        onCommit01(xNew01, yNew01)
                    },
                    onDragCancel = {
                        isDragging = false
                    }
                )
            }
    ) {
        AsyncImage(
            model = imageModel,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}