package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.progr.owlgame.dagger.OwlGameComponent
import pro.progr.owlgame.data.db.GardenType

@Composable
inline fun <reified VM : ViewModel> DaggerGardenZoneViewModel(component: OwlGameComponent,
                                                       id: String,
                                                       gardenType: GardenType) : VM {

    val factory = component
        .gardenZoneViewModelFactory()

    factory.gardenId = id
    factory.gardenType = gardenType
    return viewModel(factory = factory)
}