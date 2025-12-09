package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.progr.owlgame.dagger.AppComponent

@Composable
inline fun <reified VM : ViewModel> DaggerWidgetViewModel(component: AppComponent) : VM {

    val factory : WidgetViewModelFactory = component
        .widgetViewModelFactory()

    return viewModel(factory = factory)
}