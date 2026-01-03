package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
        Scaffold(
            topBar = {
                Box(modifier = Modifier.statusBarsPadding()) {
                    BuildingBar(navController, bWithData)
                }
            },
            content = { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    InBuilding(bWithData)
                }
            }
        )

    }
}
