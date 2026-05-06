package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.domain.repository.ExpeditionMedalRepository
import pro.progr.owlgame.presentation.viewmodel.BuildingFacadeViewModel
import javax.inject.Inject

class BuildingFacadeViewModelFactory @Inject constructor(
    private val medalsRepository: ExpeditionMedalRepository
) : ViewModelProvider.Factory {
    var animalId: String? = null;

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BuildingFacadeViewModel::class.java)) {
            return BuildingFacadeViewModel(medalsRepository, animalId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}