package pro.progr.owlgame.presentation.ui.building

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pro.progr.owlgame.data.db.Furniture
import pro.progr.owlgame.data.db.RoomEntity

@Composable
fun InRoom(
    room: RoomEntity,
    furniture: List<Furniture>,
    onUpdateFurniturePos: (id: String, x01: Float, y01: Float) -> Unit,
    newFurnitureId: String? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        DraggableSizedImageOverlay(
            backgroundModel = room.imageUrl,           // картинка стены
            items = furniture.filter { it.roomId == room.id },
            keyOf = { it.id },
            x01Of = { it.x },
            y01Of = { it.y },
            width01Of = { it.width },
            height01Of = { it.height },
            itemImageModelOf = { it.imageUrl },        // мебель — картинка, не иконка
            isNewOf = { it.id == newFurnitureId },
            onCommit01 = { f, x, y -> onUpdateFurniturePos(f.id, x, y) },
            // можно настроить дефолтные размеры, если в БД 0
            defaultWidth01 = 0.22f,
            defaultHeight01 = 0.35f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}