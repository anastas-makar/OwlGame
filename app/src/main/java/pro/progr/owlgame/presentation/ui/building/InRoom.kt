package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    newFurnitureId: String? = null,
) {
    val roomViewModel = DaggerRoomViewModel<RoomViewModel>(component, room.id)
    fabViewModel.fabActions.value = listOf(
        FabAction(
            text = "Поставить",
            color = Color.DarkGray,
            onClick = {
                roomViewModel.selectFurnitureItemState.value = true
            }
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        DraggableSizedImageOverlay(
            backgroundModel = room.imageUrl,           // картинка стены
            items = roomViewModel.furnitureItems.value,
            keyOf = { it.id },
            x01Of = { it.x },
            y01Of = { it.y },
            width01Of = { it.width },
            height01Of = { it.height },
            itemImageModelOf = { it.imageUrl },        // мебель — картинка, не иконка
            isNewOf = { it.id == newFurnitureId },
            onCommit01 = { f, x, y -> roomViewModel.updatePos(f.id, x, y) },
            // можно настроить дефолтные размеры, если в БД 0
            defaultWidth01 = 0.22f,
            defaultHeight01 = 0.35f,
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (roomViewModel.selectFurnitureItemState.value) {
        SelectFurnitureScreen(roomViewModel, fabViewModel)
    }
}