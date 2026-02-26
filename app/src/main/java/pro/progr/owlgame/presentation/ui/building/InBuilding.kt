package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.dagger.OwlGameComponent
import pro.progr.owlgame.data.db.BuildingWithData
import pro.progr.owlgame.presentation.ui.fab.FabViewModel
import pro.progr.owlgame.presentation.ui.model.GalleryItem

@Composable
fun InBuilding(
    data: BuildingWithData,
    component: OwlGameComponent,
    fabViewModel: FabViewModel,
    diamondDao: PurchaseInterface,
    modifier: Modifier = Modifier) {
    val rooms = remember(data.rooms) { data.rooms.sortedBy { it.roomNumber } }
    val gardens = remember(data.gardens) { data.gardens.sortedBy { it.gardenNumber } }

    val items = remember(data.building, rooms, gardens) {
        buildList<GalleryItem> {
            add(GalleryItem.BuildingItem(data))
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
                is GalleryItem.BuildingItem -> BuildingFacade(s.building, fabViewModel)
                is GalleryItem.RoomItem -> InRoom(s.room, component, fabViewModel, diamondDao,
                    onMap = data.building.mapId != null,
                    animal = data.animal,
                    onOpenCraft = nav)
                is GalleryItem.GardenItem -> InGardenZone(s.garden, component, fabViewModel,
                    onMap = data.building.mapId != null)
                null -> Unit
            }
        }
    }
}
