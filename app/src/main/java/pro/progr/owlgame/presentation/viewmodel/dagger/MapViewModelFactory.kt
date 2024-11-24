package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.domain.FoundTownUseCase
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import javax.inject.Inject

class MapViewModelFactory @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val foundTownUseCase: FoundTownUseCase
) : ViewModelProvider.Factory {
    var id : String = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(mapsRepository, id, foundTownUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}