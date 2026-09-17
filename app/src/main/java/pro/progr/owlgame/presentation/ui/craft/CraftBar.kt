package pro.progr.owlgame.presentation.ui.craft

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pro.progr.owlgame.presentation.ui.NavIcon
import pro.progr.owlgame.R

@Composable
fun CraftBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.craft_screen_title))
        },
        navigationIcon = {
            NavIcon(navController)
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}
