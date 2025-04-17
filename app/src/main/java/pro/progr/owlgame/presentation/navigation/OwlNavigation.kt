package pro.progr.owlgame.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pro.progr.owlgame.presentation.ui.InPouchScreen
import pro.progr.owlgame.presentation.ui.MapScreen
import pro.progr.owlgame.presentation.ui.PouchesScreen
import pro.progr.owlgame.presentation.ui.MapsListScreen

import pro.progr.diamondapi.DiamondInterface
import pro.progr.owlgame.presentation.ui.AnimalSearchingScreen

@Composable
fun OwlNavigation(startDestination : String = "towns",
                  backToMain : () -> Unit,
                  diamondDao: DiamondInterface) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("towns") {
            MapsListScreen(backToMain, navController)
        }
        composable("pouch") {
            PouchesScreen(backToMain, navController)
        }
        composable("inPouch/{id}") { backStackEntry ->

            BackHandler {
                backToMain()
            }

            val pouchId = backStackEntry.arguments?.getString("id")
            pouchId?.let {pId ->
                InPouchScreen(backToMain, navController, pId)
            }
        }
        composable(
            route = "map/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")

            id?.let {
                MapScreen(navController, id, diamondDao)
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
                AnimalSearchingScreen(navController, id)
            }
        }

    }
}