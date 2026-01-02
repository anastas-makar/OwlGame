package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.BuildingWithData
import pro.progr.owlgame.data.db.Garden
import pro.progr.owlgame.data.db.RoomEntity
import pro.progr.owlgame.presentation.ui.model.GalleryItem

@Composable
fun InBuilding(
    data: BuildingWithData,
    modifier: Modifier = Modifier) {
    val rooms = remember(data.rooms) { data.rooms.sortedBy { it.roomNumber } }
    val gardens = remember(data.gardens) { data.gardens.sortedBy { it.gardenNumber } }

    val items = remember(data.building, rooms, gardens) {
        buildList<GalleryItem> {
            add(GalleryItem.BuildingItem(data.building))
            addAll(rooms.map { GalleryItem.RoomItem(it) })
            addAll(gardens.map { GalleryItem.GardenItem(it) })
        }
    }

    var selectedKey by rememberSaveable { mutableStateOf(items.firstOrNull()?.key) }
    val selected = remember(items, selectedKey) {
        items.firstOrNull { it.key == selectedKey } ?: items.firstOrNull()
    }

    Column(modifier = modifier.fillMaxSize()) {

        // Верхняя галерея
        GalleryRow(
            items = items,
            selectedKey = selected?.key,
            onSelect = { selectedKey = it }
        )

        // Большой активный блок
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (val s = selected) {
                is GalleryItem.BuildingItem -> BuildingFasade(s.building)
                is GalleryItem.RoomItem -> InRoom(s.room)
                is GalleryItem.GardenItem -> InGarden(s.garden)
                null -> Unit
            }
        }
    }
}
