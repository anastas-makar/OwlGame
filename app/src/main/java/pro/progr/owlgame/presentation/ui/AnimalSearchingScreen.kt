package pro.progr.owlgame.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import pro.progr.owlgame.presentation.viewmodel.AnimalViewModel
import pro.progr.owlgame.presentation.viewmodel.dagger.DaggerAnimalViewModel

@Composable
fun AnimalSearchingScreen(
    backToMain: () -> Unit,
    navController: NavHostController,
    id: String,
    animalViewModel : AnimalViewModel = DaggerAnimalViewModel(id = id)
) {
    val animalState = animalViewModel.animal.collectAsState(initial = null)

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
                Text(text = if (animalState.value != null)
                    "${animalState.value?.name} может поселиться здесь: "
                else "")


            }
        }
    )
}