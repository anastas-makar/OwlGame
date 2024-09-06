package pro.progr.owlgame.presentation.ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun WidgetScreen(navController: NavHostController) {
    Button(onClick = {
        navController.navigate("owl_navigation")
    }) {
        Text("К спасению совы")
    }
}