package pro.progr.owlgame.presentation.ui.map

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.dagger.OwlGameComponent
import pro.progr.owlgame.domain.model.MapWithDataModel
import pro.progr.owlgame.domain.model.MapType
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerExpeditionPreparationViewModel

@Composable
fun MapScreen(
    navController: NavHostController,
    diamondDao: PurchaseInterface,
    component: OwlGameComponent,
    mapViewModel: MapViewModel) {
    val map = mapViewModel.map.collectAsState(initial = MapWithDataModel("", "", "", MapType.LOADING))

    when(map.value.type) {
        MapType.FREE -> FreeMapScreen(
            navController,
            diamondDao,
            mapViewModel,
            map)
        MapType.TOWN -> TownScreen(
            navController,
            diamondDao,
            mapViewModel,
            map)
        MapType.OCCUPIED -> OccupiedMapScreen(
            navController,
            diamondDao,
            mapViewModel,
            map,
            DaggerExpeditionPreparationViewModel(component, map.value.id))
        MapType.EXPEDITION -> ExpeditionScreen(
            navController,
            diamondDao,
            mapViewModel,
            map)
        MapType.LOADING -> Text("Загрузка")
    }
}