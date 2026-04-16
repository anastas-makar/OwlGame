package pro.progr.owlgame.presentation.ui

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pro.progr.owlgame.domain.model.BuildingWithDataModel

@Composable
fun BuildingBar(navController: NavHostController,  bWithData: BuildingWithDataModel) {
    TopAppBar(
        title = {
            Text(text = bWithData.name)
        },
        navigationIcon = {
            NavIcon(navController)
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}