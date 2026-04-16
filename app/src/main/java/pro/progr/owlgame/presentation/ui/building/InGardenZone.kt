package pro.progr.owlgame.presentation.ui.building

import androidx.compose.runtime.Composable
import pro.progr.owlgame.dagger.OwlGameComponent
import pro.progr.owlgame.domain.model.GardenModel
import pro.progr.owlgame.domain.model.GardenType
import pro.progr.owlgame.presentation.ui.fab.FabViewModel

@Composable
fun InGardenZone(
    garden: GardenModel,
    component: OwlGameComponent,
    fabViewModel: FabViewModel,
    onMap: Boolean = false
) {
    when (garden.gardenType) {
        GardenType.GARDEN -> GardenItems(garden, component, fabViewModel, onMap)
        GardenType.POOL -> GardenItems(garden, component, fabViewModel, onMap)
        GardenType.KITCHEN_GARDEN -> KitchenGardenItems(garden, component, fabViewModel, onMap)
    }
}