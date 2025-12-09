package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.progr.owlgame.dagger.AppComponent

@Composable
inline fun <reified VM : ViewModel> DaggerMapViewModel(component: AppComponent,
                                                       id: String) : VM {

    val factory = component
        .mapViewModelFactory()

    factory.id = id
    return viewModel(factory = factory)
}