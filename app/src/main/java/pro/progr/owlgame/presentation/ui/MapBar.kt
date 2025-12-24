package pro.progr.owlgame.presentation.ui

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pro.progr.owlgame.data.db.MapType
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import pro.progr.owlgame.presentation.ui.model.MapData

@Composable
fun MapBar(navController: NavHostController, mapViewModel: MapViewModel) {

    val mapState = mapViewModel.map.collectAsState(initial = MapData("", "", "", MapType.FREE))

    TopAppBar(
        title = {
            Text(text = mapState.value.name)
        },
        navigationIcon = {
            NavIcon(navController)
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}