package pro.progr.owlgame.presentation.ui

import android.net.Uri
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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pro.progr.owlgame.presentation.viewmodel.TownsViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerViewModel

@Composable
fun TownsListScreen(
    backToMain: () -> Unit,
    navController: NavHostController,
    townsViewModel: TownsViewModel = DaggerViewModel()
) {
    val townsList = townsViewModel.townsList.collectAsState(initial = emptyList())
    val mapsList = townsViewModel.maps.value ?: emptyList()

    townsViewModel.loadMaps()

    Scaffold(
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                TownsListBar(backToMain)
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
                    itemsIndexed(mapsList) { _, map ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize()
                        ) {
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
                        }
                    }
                }
            }
        }
    )
}
