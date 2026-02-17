package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.dagger.OwlGameComponent
import pro.progr.owlgame.data.db.RoomEntity
import pro.progr.owlgame.presentation.ui.SelectFurnitureScreen
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.ui.fab.FabViewModel
import pro.progr.owlgame.presentation.viewmodel.RoomViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerRoomViewModel

@Composable
fun InRoom(
    room: RoomEntity,
    component: OwlGameComponent,
    fabViewModel: FabViewModel,
    diamondDao: PurchaseInterface,
    onMap: Boolean = false,
) {
    val roomViewModel = DaggerRoomViewModel<RoomViewModel>(component, room.id)
    val furniture = roomViewModel.furnitureItems.collectAsState(initial = emptyList())
    val availableFurniture = roomViewModel.availableFurnitureItems.collectAsState(initial = emptyList())

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    fabViewModel.fabActions.value = listOf(
        FabAction(
            text = "Поставить мебель",
            color = Color.DarkGray,
            onClick = {
                roomViewModel.selectFurnitureItemState.value = true
            }
        )
    )

    fabViewModel.showFab.value = onMap && availableFurniture.value.isNotEmpty()

    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
    ) {
        DraggableSizedImageOverlay(
            backgroundModel = room.imageUrl,           // картинка стены
            items = furniture.value,
            keyOf = { it.id },
            x01Of = { it.x },
            y01Of = { it.y },
            width01Of = { it.width },
            height01Of = { it.height },
            itemImageModelOf = { it.imageUrl },        // мебель — картинка, не иконка
            isNewOf = { it.x == 0f && it.y == 0f },
            onCommit01 = { f, x, y -> roomViewModel.updatePos(f.id, x, y) },
            // можно настроить дефолтные размеры, если в БД 0
            defaultWidth01 = 0.22f,
            defaultHeight01 = 0.35f,
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (roomViewModel.selectFurnitureItemState.value) {
        SelectFurnitureScreen(roomViewModel,
            fabViewModel,
            diamondDao,
            scope,
            snackbarHostState,
            availableFurniture)
    }
}