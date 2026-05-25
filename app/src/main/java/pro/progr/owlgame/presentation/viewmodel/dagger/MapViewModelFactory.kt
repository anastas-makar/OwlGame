package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.domain.repository.BuildingsRepository
import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.domain.repository.SlotsRepository
import pro.progr.owlgame.domain.repository.StreetsRepository
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import javax.inject.Inject

class MapViewModelFactory @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val buildingsRepository: BuildingsRepository,
    private val slotsRepository: SlotsRepository,
    private val streetsRepository: StreetsRepository,
    private val expeditionsRepository: ExpeditionsRepository
) : ViewModelProvider.Factory {
    var id : String = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(
                mapsRepository,
                buildingsRepository,
                streetsRepository,
                slotsRepository,
                expeditionsRepository,
                id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}