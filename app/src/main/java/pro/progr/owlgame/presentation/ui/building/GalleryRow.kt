package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.presentation.ui.model.GalleryItem

@Composable
fun GalleryRow(
    items: List<GalleryItem>,
    selectedKey: String?,
    onSelect: (String) -> Unit
) {
    androidx.compose.foundation.lazy.LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            count = items.size,
            key = { idx -> items[idx].key }
        ) { idx ->
            val item = items[idx]
            val isSelected = item.key == selectedKey

            Thumbnail(
                imageUrl = item.imageUrl,
                isSelected = isSelected,
                onClick = { onSelect(item.key) }
            )
        }
    }
}