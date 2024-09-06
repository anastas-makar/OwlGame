package pro.progr.owlgame.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pro.progr.owlgame.presentation.ui.TownsListScreen

@Composable
fun OwlNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "towns") {
        composable("towns") {
            TownsListScreen()
        }
        // Другие маршруты модуля
    }
}