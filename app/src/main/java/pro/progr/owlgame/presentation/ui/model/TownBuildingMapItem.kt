package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.domain.model.BuildingWithAnimalModel

data class TownBuildingMapItem(
    val building: BuildingWithAnimalModel
) : TownMapItem {
    override val id: String = building.id
    override val x: Float = building.x
    override val y: Float = building.y
}
