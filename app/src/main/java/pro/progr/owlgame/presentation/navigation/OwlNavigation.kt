package pro.progr.owlgame.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pro.progr.owlgame.presentation.ui.TownsListScreen

@Composable
fun OwlNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "towns") {
        composable("towns") {
            TownsListScreen()
        }
        // Другие маршруты модуля
    }
}