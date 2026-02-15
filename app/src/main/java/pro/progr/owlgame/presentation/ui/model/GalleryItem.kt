package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.data.db.BuildingWithData
import pro.progr.owlgame.data.db.Garden
import pro.progr.owlgame.data.db.RoomEntity

sealed interface GalleryItem {
    val key: String
    val imageUrl: String

    data class BuildingItem(val building: BuildingWithData) : GalleryItem {
        override val key: String = "b:${building.building.id}"
        override val imageUrl: String = building.building.imageUrl
    }

    data class RoomItem(val room: RoomEntity) : GalleryItem {
        override val key: String = "r:${room.id}"
        override val imageUrl: String = room.imageUrl
    }

    data class GardenItem(val garden: Garden) : GalleryItem {
        override val key: String = "g:${garden.id}"
        override val imageUrl: String = garden.imageUrl
    }
}
