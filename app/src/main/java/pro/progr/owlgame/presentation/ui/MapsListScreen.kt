package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pro.progr.owlgame.presentation.viewmodel.MapsViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerViewModel

@Composable
fun MapsListScreen(
    backToMain: () -> Unit,
    navController: NavHostController,
    mapsViewModel: MapsViewModel = DaggerViewModel()
) {
    val mapsList = mapsViewModel.loadMaps().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                MapsListBar(backToMain)
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(mapsList.value) { _, map ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize()
                        ) {
                            Column {
                                AsyncImage(
                                    model = map.imageUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            navController.navigate("map/${map.id}")
                                        }
                                )

                                Text(text = if (map.town != null) map.town.name else map.name,
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(10.dp)
                                        .align(Alignment.CenterHorizontally))

                            }
                        }
                    }
                }
            }
        }
    )
}
