package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.progr.owlgame.dagger.OwlGameComponent

@Composable
inline fun <reified VM : ViewModel> DaggerAnimalViewModel(component: OwlGameComponent,
                                                          id: String) : VM {

    val factory = component
        .animalViewModelFactory()

    factory.animalId = id
    return viewModel(factory = factory, key = id)
}