package pro.progr.owlgame.presentation.ui.craft

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pro.progr.owlgame.presentation.ui.NavIcon

@Composable
fun CraftBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(text = "Приготовление")
        },
        navigationIcon = {
            NavIcon(navController)
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}