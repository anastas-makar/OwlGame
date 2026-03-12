package pro.progr.owlgame.presentation.ui.map

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.MapType
import pro.progr.owlgame.presentation.viewmodel.MapViewModel

@Composable
fun MapScreen(
    navController: NavHostController,
    diamondDao: PurchaseInterface,
    mapViewModel: MapViewModel) {

    when(mapViewModel.map.value.type) {
        MapType.FREE -> FreeMapScreen(
            navController,
            diamondDao,
            mapViewModel)
        MapType.TOWN -> TownScreen(
                navController,
                diamondDao,
                mapViewModel)
        MapType.OCCUPIED -> OccupiedMapScreen(
                navController,
                diamondDao,
                mapViewModel)
        MapType.EXPEDITION -> ExpeditionScreen(
                navController,
                diamondDao,
                mapViewModel)
    }
}