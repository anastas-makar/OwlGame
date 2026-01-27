package pro.progr.owlgame.presentation.ui.building

import androidx.compose.runtime.Composable
import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.presentation.ui.fab.FabViewModel

@Composable
fun BuildingFacade(building: Building, fabViewModel: FabViewModel) {
    fabViewModel.showFab.value = false
    LargeImage(imageUrl = building.imageUrl)
}