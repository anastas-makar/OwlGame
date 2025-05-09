package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerAnimalViewModel
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.unit.dp

@Composable
fun AnimalSearchingScreen(
    backToMain: () -> Unit,
    navController: NavHostController,
    animalId: String,
    animalViewModel : AnimalViewModel = DaggerAnimalViewModel(id = animalId)
) {
    val animalState = animalViewModel.animal.collectAsState(initial = null)

    val mapsState = animalViewModel.mapsWithUninhabitedBuildings.collectAsState(initial = emptyList())

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
                    AsyncImage(model = animal.imagePath,
                        contentDescription = "Изображение ${animal.name}")
                }
                if (animalState.value != null) {
                    Text(text =
                    "${animalState.value?.name} может поселиться здесь: ")
                }

                LazyColumn {

                    itemsIndexed(mapsState.value) { _, mapWithData ->
                        Text(text = "В городе ${mapWithData.town?.name} можно выбрать дом:")

                        LazyColumn {
                            itemsIndexed(mapsState.value) { _, mapWithData ->
                                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                    Text(text = "В городе ${mapWithData.town?.name} можно выбрать дом:")

                                    AsyncImage(
                                        model = mapWithData.mapEntity.imagePath,
                                        contentDescription = "Карта города",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    LazyVerticalGrid(
                                        columns = GridCells.Fixed(3),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(max = 400.dp), // можно настроить
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalArrangement = Arrangement.spacedBy(8.dp),
                                        userScrollEnabled = false // чтобы не мешал основному скроллу
                                    ) {
                                        itemsIndexed(mapWithData.slots) { _, slotWithBuilding ->
                                            AsyncImage(
                                                model = slotWithBuilding.building.imageUrl,
                                                contentDescription = "Дом",
                                                modifier = Modifier
                                                    .aspectRatio(1f)
                                                    .fillMaxWidth()
                                                    .clickable {
                                                        animalViewModel.saveAnimalInBuilding(slotWithBuilding.building.id,
                                                            animalId)
                                                    }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    )
}