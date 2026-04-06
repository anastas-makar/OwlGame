package pro.progr.owlgame.presentation.ui.map

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.dagger.OwlGameComponent
import pro.progr.owlgame.presentation.ui.model.MapData
import pro.progr.owlgame.presentation.ui.model.MapTypeUI
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerExpeditionPreparationViewModel

@Composable
fun MapScreen(
    navController: NavHostController,
    diamondDao: PurchaseInterface,
    component: OwlGameComponent,
    mapViewModel: MapViewModel) {
    val map = mapViewModel.map.collectAsState(initial = MapData("", "", "", MapTypeUI.LOADING))

    when(map.value.type) {
        MapTypeUI.FREE -> FreeMapScreen(
            navController,
            diamondDao,
            mapViewModel,
            map)
        MapTypeUI.TOWN -> TownScreen(
            navController,
            diamondDao,
            mapViewModel,
            map)
        MapTypeUI.OCCUPIED -> OccupiedMapScreen(
            navController,
            diamondDao,
            mapViewModel,
            map,
            DaggerExpeditionPreparationViewModel(component, map.value.id))
        MapTypeUI.EXPEDITION -> ExpeditionScreen(
            navController,
            diamondDao,
            mapViewModel,
            map)
        MapTypeUI.LOADING -> Text("Загрузка")
    }
}