package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import pro.progr.owlgame.presentation.viewmodel.OccupiedMapViewModel
import javax.inject.Inject

class OccupiedMapViewModelFactory @Inject constructor(
    private val expeditionsRepository: ExpeditionsRepository,
    private val animalsRepository: AnimalsRepository
) : ViewModelProvider.Factory {
    var mapId : String = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OccupiedMapViewModel::class.java)) {
            return OccupiedMapViewModel(
                expeditionsRepository,
                animalsRepository,
                mapId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}