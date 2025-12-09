package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.progr.owlgame.dagger.AppComponent

@Composable
inline fun <reified VM : ViewModel> DaggerMapsViewModel(component: AppComponent): VM {

    val factory = component
        .mapsViewModelFactory()
    return viewModel(factory = factory)
}