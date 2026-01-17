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
    fabViewModel: FabViewModel
) {
    when (garden.gardenType) {
        GardenType.GARDEN -> GardenItems(garden, component, fabViewModel)
        GardenType.POOL -> InPool(garden/*, vm*/)
        GardenType.KITCHEN_GARDEN -> InKitchenGarden(garden/*, vm*/)
    }
}