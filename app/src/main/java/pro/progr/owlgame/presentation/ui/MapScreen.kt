package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pro.progr.owlgame.data.web.Map
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerMapViewModel

@Composable
fun MapScreen(navController: NavHostController,
              id: String,
              mapViewModel: MapViewModel = DaggerMapViewModel(id)
) {
    val map = mapViewModel.map.collectAsState(initial = Map("", "", ""))

    Scaffold(
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                //TownBar(navController, mapViewModel)
            }
        },
        content = { innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()) {

                AsyncImage(
                    model = map.value.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }


        }
    )
}