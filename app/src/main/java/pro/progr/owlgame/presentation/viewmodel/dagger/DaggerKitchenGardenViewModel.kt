package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.progr.owlgame.dagger.OwlGameComponent
import pro.progr.owlgame.data.db.GardenType

@Composable
inline fun <reified VM : ViewModel> DaggerKitchenGardenViewModel(component: OwlGameComponent,
                                                       id: String) : VM {

    val factory = component
        .kitchenGardenViewModelFactory()

    factory.gardenId = id
    return viewModel(factory = factory, key = id)
}