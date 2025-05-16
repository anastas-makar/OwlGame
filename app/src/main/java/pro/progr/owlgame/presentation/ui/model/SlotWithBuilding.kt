package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.data.db.BuildingWithAnimal
import pro.progr.owlgame.data.db.Slot

data class SlotWithBuilding (
    val slot: Slot,
    val building: BuildingWithAnimal?
)
