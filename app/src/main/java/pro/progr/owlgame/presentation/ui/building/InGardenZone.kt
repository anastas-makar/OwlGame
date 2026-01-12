package pro.progr.owlgame.presentation.ui.building

import androidx.compose.runtime.Composable
import pro.progr.owlgame.data.db.Garden
import pro.progr.owlgame.data.db.GardenType

@Composable
fun InGardenZone(
    garden: Garden,
    // дальше — то, что нужно для конкретной зоны (обычно vm/стейты)
    //vm: GardenZoneViewModel,
) {
    when (garden.gardenType) {
        GardenType.GARDEN -> InGarden(garden/*, vm*/)
        GardenType.POOL -> InPool(garden/*, vm*/)
        GardenType.KITCHEN_GARDEN -> InKitchenGarden(garden/*, vm*/)
    }
}