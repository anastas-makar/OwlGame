package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pro.progr.owlgame.presentation.viewmodel.AnimalViewModel
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AnimalSearchingScreen(
    backToMain: () -> Unit,
    navController: NavHostController,
    animalId: String,
    animalViewModel: AnimalViewModel
) {
    val animalState = animalViewModel.animal.collectAsState(initial = null)

    val mapsState =
        animalViewModel.mapsWithUninhabitedBuildings.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            Box(modifier = Modifier.statusBarsPadding()) {
                AnimalSearchingBar(backToMain, animalState)
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {
                animalState.value?.let { animal ->

                    Row(modifier = Modifier.padding(16.dp, 10.dp)) {
                        AsyncImage(
                            model = animal.imagePath,
                            contentDescription = "Изображение ${animal.name}"
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "${animalState.value?.name}",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    itemsIndexed(mapsState.value) { _, mapData ->
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Row(modifier = Modifier.padding(16.dp, 10.dp)) {
                                AsyncImage(
                                    model = mapData.imageUrl,
                                    contentDescription = "Карта города",
                                    modifier = Modifier
                                        .size(100.dp)
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(text = "В городе ${mapData.name} можно выбрать дом:")

                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Грид с фиксированной высотой, чтобы не ломал скролл
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(16.dp, 5.dp)
                                    .heightIn(max = 400.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                userScrollEnabled = false
                            ) {
                                itemsIndexed(mapData.buildings
                                    .filter {building ->
                                        building.animal == null
                                    }
                                ) { _, building ->
                                        AsyncImage(
                                            model = building.building.imageUrl,
                                            contentDescription = "Дом",
                                            modifier = Modifier
                                                .aspectRatio(1f)
                                                .fillMaxWidth()
                                                .clickable {
                                                    animalViewModel.saveAnimalInBuilding(
                                                        building.building.id,
                                                        animalId
                                                    )
                                                    navController.navigate("map/${mapData.id}")
                                                }
                                        )

                                }
                            }
                        }
                    }
                }

            }
        }
    )
}