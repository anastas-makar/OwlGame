package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.dagger.OwlGameComponent
import pro.progr.owlgame.presentation.ui.building.InBuilding
import pro.progr.owlgame.presentation.ui.fab.ExpandableFloatingActionButton
import pro.progr.owlgame.presentation.ui.fab.FabAction
import pro.progr.owlgame.presentation.ui.fab.FabViewModel
import pro.progr.owlgame.presentation.viewmodel.BuildingViewModel

@Composable
fun BuildingScreen(
    navController: NavHostController,
    diamondDao: PurchaseInterface,
    buildingViewModel: BuildingViewModel,
    fabViewModel: FabViewModel,
    component: OwlGameComponent
) {
    val data by buildingViewModel.observe().collectAsStateWithLifecycle(initialValue = null)


    data?.let { bWithData ->
        Scaffold(
            topBar = {
                Box(modifier = Modifier.statusBarsPadding()) {
                    BuildingBar(navController, bWithData)
                }
            },
            floatingActionButton = {
                if (fabViewModel.showFab.value) {
                    ExpandableFloatingActionButton(
                        expanded = fabViewModel.fabExpanded.value,
                        onExpandedChange = { fabViewModel.fabExpanded.value = it },
                        actions = listOf(
                            FabAction(
                                text = "Построить дом",
                                color = Color.DarkGray,
                                onClick = {  }
                            ),
                            FabAction(
                                text = "Построить замок",
                                color = Color.DarkGray,
                                onClick = {  }
                            )
                        ),
                        modifier = Modifier.navigationBarsPadding()
                    )
                }
            },
            content = { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    InBuilding(bWithData, component)

                    if (fabViewModel.fabExpanded.value) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.35f))
                                .pointerInput(Unit) {
                                    detectTapGestures(onTap = { fabViewModel.fabExpanded.value = false })
                                }
                        )
                    }
                }
            }
        )

    }
}
