package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.progr.owlgame.dagger.DaggerAppComponent

@Composable
inline fun <reified VM : ViewModel> DaggerViewModel(id: String) : VM {
    val factory = DaggerAppComponent.create().viewModelFactory()
    factory.id = id
    return viewModel(factory = factory)
}