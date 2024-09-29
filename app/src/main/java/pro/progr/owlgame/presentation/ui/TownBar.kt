package pro.progr.owlgame.presentation.ui

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pro.progr.owlgame.data.db.Town
import pro.progr.owlgame.presentation.viewmodel.TownViewModel

@Composable
fun TownBar(navController: NavHostController, townViewModel: TownViewModel) {

    val townState = townViewModel.town.collectAsState(initial = Town("", ""))

    TopAppBar(
        title = {
            Text(text = townState.value.name)
        },
        navigationIcon = {
            NavIcon(navController)
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}