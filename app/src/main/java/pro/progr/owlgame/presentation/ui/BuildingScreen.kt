package pro.progr.owlgame.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.presentation.ui.building.InBuilding
import pro.progr.owlgame.presentation.viewmodel.BuildingViewModel

@Composable
fun BuildingScreen(
    navController: NavHostController,
    diamondDao: PurchaseInterface,
    buildingViewModel: BuildingViewModel
) {
    val data by buildingViewModel.observe().collectAsStateWithLifecycle(initialValue = null)
    data?.let { bWithData ->
        InBuilding(bWithData)
    }
}
