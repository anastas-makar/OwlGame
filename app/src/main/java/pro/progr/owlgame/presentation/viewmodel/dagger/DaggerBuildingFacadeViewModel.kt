package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.progr.owlgame.dagger.OwlGameComponent

@Composable
inline fun <reified VM : ViewModel> DaggerBuildingFacadeViewModel(
    component: OwlGameComponent,
    animalId: String?) : VM {

    val factory = component
        .buildingFacadeViewModelFactory()
    factory.animalId = animalId

    return viewModel(factory = factory)
}