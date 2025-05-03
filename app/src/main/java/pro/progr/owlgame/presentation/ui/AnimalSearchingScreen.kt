package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
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

@Composable
fun AnimalSearchingScreen(
    backToMain: () -> Unit,
    navController: NavHostController,
    id: String,
    animalViewModel : AnimalViewModel = DaggerAnimalViewModel(id = id)
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
                    }

                }
            }
        }
    )
}