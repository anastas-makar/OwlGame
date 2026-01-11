package pro.progr.owlgame.presentation.ui.mapicon

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun <T> DraggableImageOverlay(
    backgroundModel: Any?,
    items: List<T>,
    modifier: Modifier = Modifier,
    itemSize: Dp = 50.dp,
    keyOf: (T) -> Any,
    x01Of: (T) -> Float,
    y01Of: (T) -> Float,
    // “новый элемент” лучше задавать снаружи (по id), а не угадывать по (0,0)
    isNewOf: (T) -> Boolean = { false },
    iconPainterOf: @Composable (T) -> Painter,
    onCommit01: (T, Float, Float) -> Unit,
    onTouched: ((T) -> Unit)? = null,
) {
    var bgSizePx by remember { mutableStateOf(IntSize.Zero) }

    Box(modifier = modifier.fillMaxWidth()) {

        AsyncImage(
            model = backgroundModel,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { bgSizePx = it }
        )

        if (bgSizePx.width == 0 || bgSizePx.height == 0) return@Box

        items.forEach { item ->
            key(keyOf(item)) {
                DraggableIcon(
                    x01 = x01Of(item),
                    y01 = y01Of(item),
                    mapSizePx = bgSizePx,
                    iconPainter = iconPainterOf(item),
                    itemSize = itemSize,
                    isNew = isNewOf(item),
                    onCommit01 = { x, y -> onCommit01(item, x, y) },
                    onTouched = { onTouched?.invoke(item) }
                )
            }
        }
    }
}

