package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.progr.owlgame.dagger.OwlGameComponent

@Composable
inline fun <reified VM : ViewModel> DaggerBuildingViewModel(component: OwlGameComponent,
                                                       id: String) : VM {

    val factory = component
        .buildingViewModelFactory()

    factory.id = id
    return viewModel(factory = factory)
}