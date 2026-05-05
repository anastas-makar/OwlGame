package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.progr.owlgame.dagger.OwlGameComponent

@Composable
inline fun <reified VM : ViewModel> DaggerFreeMapViewModel(component: OwlGameComponent, mapId: String) : VM {

    val factory = component
        .freeMapViewModelFactory()
    factory.mapId = mapId

    return viewModel(factory = factory)
}