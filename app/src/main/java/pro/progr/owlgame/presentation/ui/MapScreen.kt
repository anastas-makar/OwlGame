package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val foundTown = mapViewModel.foundTown.collectAsState(initial = false)

    Scaffold(
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                MapBar(navController, mapViewModel)
            }
        },
        content = { innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()) {

                Column(modifier = Modifier
                    .fillMaxWidth()) {

                    if (!foundTown.value) {
                        TextButton(onClick = {
                            mapViewModel.foundTown(map.value, "Кубинка 1")
                        }, modifier = Modifier.align(CenterHorizontally)) {
                            Text(text = "Основать город")
                        }
                    }

                    AsyncImage(
                        model = map.value.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                if (foundTown.value) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White.copy(alpha = 0.8f))
                    ) {

                    }

                }

            }


        }
    )
}