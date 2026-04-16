package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.domain.model.BuildingWithDataModel
import pro.progr.owlgame.domain.model.GardenModel
import pro.progr.owlgame.domain.model.RoomModel

sealed interface GalleryItem {
    val key: String
    val imageUrl: String

    data class BuildingItem(val building: BuildingWithDataModel) : GalleryItem {
        override val key: String = "b:${building.id}"
        override val imageUrl: String = building.imageUrl
    }

    data class RoomItem(val room: RoomModel) : GalleryItem {
        override val key: String = "r:${room.id}"
        override val imageUrl: String = room.imageUrl
    }

    data class GardenItem(val garden: GardenModel) : GalleryItem {
        override val key: String = "g:${garden.id}"
        override val imageUrl: String = garden.imageUrl
    }
}
