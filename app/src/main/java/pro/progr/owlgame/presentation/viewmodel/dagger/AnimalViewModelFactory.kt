package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.BuildingsRepository
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.domain.usecase.GrantBuildingToAnimalUseCase
import pro.progr.owlgame.presentation.viewmodel.AnimalViewModel
import javax.inject.Inject

class AnimalViewModelFactory @Inject constructor(
    val animalsRepository: AnimalsRepository,
    val mapsRepository: MapsRepository,
    val buildingsRepository: BuildingsRepository,
    val grantBuildingToAnimalUseCase: GrantBuildingToAnimalUseCase,
) : ViewModelProvider.Factory {
    var animalId : String = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimalViewModel::class.java)) {
            return AnimalViewModel(animalsRepository,
                mapsRepository,
                buildingsRepository,
                grantBuildingToAnimalUseCase,
                animalId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}