package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import javax.inject.Inject

class MapViewModelFactory @Inject constructor(
    private val mapsRepository: MapsRepository
) : ViewModelProvider.Factory {
    var id : String = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(mapsRepository, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}