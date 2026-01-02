package pro.progr.owlgame.presentation.ui.building

import androidx.compose.runtime.Composable
import pro.progr.owlgame.data.db.RoomEntity

@Composable
fun InRoom(room: RoomEntity) {
    LargeImage(imageUrl = room.imageUrl)
}