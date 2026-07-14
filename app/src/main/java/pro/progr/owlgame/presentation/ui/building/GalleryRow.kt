package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.presentation.ui.model.GalleryItem
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.lazy.rememberLazyListState

@Composable
fun GalleryRow(
    items: List<GalleryItem>,
    selectedKey: String?,
    onSelect: (String) -> Unit
) {
    val listState = rememberLazyListState()

    // 72 dp — миниатюра, 8 dp — расстояние до следующей.
    val spaceForNextItemPx = with(LocalDensity.current) {
        80.dp.roundToPx()
    }

    LaunchedEffect(selectedKey, items) {
        val selectedIndex = items.indexOfFirst { it.key == selectedKey }

        if (selectedIndex == -1) {
            return@LaunchedEffect
        }

        val selectedItemInfo =
            listState.layoutInfo.visibleItemsInfo.firstOrNull {
                it.index == selectedIndex
            }

        val selectedItemEnd =
            selectedItemInfo?.let { it.offset + it.size }

        val viewportEnd = listState.layoutInfo.viewportEndOffset

        /*
         * Прокручиваем, если:
         * 1. выбранный элемент вообще не виден;
         * 2. справа от него не помещается ещё одна миниатюра.
         */
        val shouldScroll =
            selectedItemInfo == null ||
                    selectedItemEnd == null ||
                    selectedItemEnd + spaceForNextItemPx > viewportEnd

        if (shouldScroll) {
            /*
             * Ставим перед выбранным элементом ещё одну миниатюру.
             * Так он не прилипает к самому левому краю, но справа
             * становится видно продолжение галереи.
             */
            val targetIndex = (selectedIndex - 1).coerceAtLeast(0)

            listState.animateScrollToItem(targetIndex)
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = items.size,
            key = { index -> items[index].key }
        ) { index ->
            val item = items[index]
            val isSelected = item.key == selectedKey

            Thumbnail(
                imageUrl = item.imageUrl,
                isSelected = isSelected,
                onClick = { onSelect(item.key) }
            )
        }
    }
}