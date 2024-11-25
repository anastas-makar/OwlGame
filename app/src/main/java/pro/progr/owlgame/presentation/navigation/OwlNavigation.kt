package pro.progr.owlgame.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pro.progr.owlgame.presentation.ui.MapScreen
import pro.progr.owlgame.presentation.ui.TownScreen
import pro.progr.owlgame.presentation.ui.TownsListScreen

@Composable
fun OwlNavigation(backToMain : () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "towns") {
        composable("towns") {
            TownsListScreen(backToMain, navController)
        }
        composable(
            route = "town/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")

            id?.let {
                TownScreen(navController, id)
            }
        }
        composable(route = "found_town/{mapId}") {

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