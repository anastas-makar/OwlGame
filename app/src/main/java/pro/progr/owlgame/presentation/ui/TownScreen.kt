package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import pro.progr.owlgame.presentation.viewmodel.TownViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerViewModel

@Composable
fun TownScreen(navController: NavHostController,
               id: String,
               townViewModel: TownViewModel = DaggerViewModel(id)) {
    Scaffold(
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                TownBar(navController, townViewModel)
            }
        },
        content = { innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()) {

                Text("Map of town")
            }


        }
    )
}