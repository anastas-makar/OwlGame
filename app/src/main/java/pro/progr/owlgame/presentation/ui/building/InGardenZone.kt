package pro.progr.owlgame.presentation.ui.building

import androidx.compose.runtime.Composable
import pro.progr.owlgame.dagger.OwlGameComponent
import pro.progr.owlgame.data.db.Garden
import pro.progr.owlgame.data.db.GardenType
import pro.progr.owlgame.presentation.ui.fab.FabViewModel

@Composable
fun InGardenZone(
    garden: Garden,
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