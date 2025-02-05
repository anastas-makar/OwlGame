package pro.progr.owlgame.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pro.progr.owlgame.presentation.ui.InPouchScreen
import pro.progr.owlgame.presentation.ui.MapScreen
import pro.progr.owlgame.presentation.ui.PouchesScreen
import pro.progr.owlgame.presentation.ui.TownScreen
import pro.progr.owlgame.presentation.ui.MapsListScreen

@Composable
fun OwlNavigation(startDestination : String = "towns", backToMain : () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("towns") {
            MapsListScreen(backToMain, navController)
        }
        composable("pouch") {
            PouchesScreen(backToMain, navController)
        }
        composable("inPouch/{id}") { backStackEntry ->
            val pouchId = backStackEntry.arguments?.getString("id")
            pouchId?.let {pId ->
                InPouchScreen(backToMain, navController, pId)
            }
        }
        composable(
            route = "town/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")

            id?.let {
                TownScreen(navController, id)
            }
        }
        composable(
            route = "map/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")

            id?.let {
                MapScreen(navController, id)
            }
        }
    }
}