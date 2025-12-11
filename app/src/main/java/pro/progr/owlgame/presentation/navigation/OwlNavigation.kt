package pro.progr.owlgame.presentation.navigation

import android.app.Application
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pro.progr.authapi.AuthInterface
import pro.progr.owlgame.presentation.ui.MapScreen
import pro.progr.owlgame.presentation.ui.PouchesScreen
import pro.progr.owlgame.presentation.ui.MapsListScreen

import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.dagger.OwlGameModule
import pro.progr.owlgame.dagger.DaggerOwlGameComponent
import pro.progr.owlgame.presentation.ui.AnimalSearchingScreen
import pro.progr.owlgame.presentation.viewmodel.AnimalViewModel
import pro.progr.owlgame.presentation.viewmodel.InPouchViewModel
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import pro.progr.owlgame.presentation.viewmodel.MapsViewModel
import pro.progr.owlgame.presentation.viewmodel.PouchesViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerAnimalViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerMapViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerMapsViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerPouchesViewModel

@Composable
fun OwlNavigation(startDestination : String = "towns",
                  backToMain : () -> Unit,
                  diamondDao: PurchaseInterface,
                  auth: AuthInterface) {
    val navController = rememberNavController()

    val application = LocalContext.current.applicationContext as Application
    val component = DaggerOwlGameComponent.builder()
        .application(application)
        .appModule(OwlGameModule(application))
        .auth(auth)
        .build()

    val mapsViewModel: MapsViewModel = DaggerMapsViewModel(component)

    val pouchesViewModel: PouchesViewModel = DaggerPouchesViewModel(component)
    val inPouchViewModel: InPouchViewModel = DaggerPouchesViewModel(component)

    NavHost(navController = navController, startDestination = startDestination) {
        composable("towns") {
            MapsListScreen(backToMain,
                navController,
                mapsViewModel)
        }
        composable("pouch") {
            PouchesScreen(backToMain,
                navController,
                pouchesViewModel,
                inPouchViewModel
            )
        }
        composable(
            route = "map/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")

            id?.let {
                val mapViewModel: MapViewModel = DaggerMapViewModel(component, id)
                MapScreen(navController,
                    diamondDao,
                    mapViewModel)
            }
        }
        //animal_searching
        composable(
            route = "animal_searching/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })) { backStackEntry ->

            BackHandler {
                backToMain()
            }

            val id = backStackEntry.arguments?.getString("id")

            id?.let {
                val animalViewModel: AnimalViewModel = DaggerAnimalViewModel(component, id)
                AnimalSearchingScreen(backToMain,
                    navController,
                    id,
                    animalViewModel
                    )
            }
        }

    }
}