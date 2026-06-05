package pro.progr.owlgame.presentation.ui.model.mapitem

import pro.progr.owlgame.domain.model.LocationWithScenesModel

data class TownLocationMapItem(
    val location: LocationWithScenesModel
) : TownMapItem {
    override val id: String = location.id
    override val x: Float = location.x
    override val y: Float = location.y
}
