package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.BuildingsRepository
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.repository.SlotsRepository
import pro.progr.owlgame.domain.FoundTownUseCase
import pro.progr.owlgame.presentation.viewmodel.BuildingViewModel
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import javax.inject.Inject

class BuildingViewModelFactory @Inject constructor(
    private val buildingsRepository: BuildingsRepository
) : ViewModelProvider.Factory {
    var id : String = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BuildingViewModel::class.java)) {
            return BuildingViewModel(
                buildingsRepository,
                id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}