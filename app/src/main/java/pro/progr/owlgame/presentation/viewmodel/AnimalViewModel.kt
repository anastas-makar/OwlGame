package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.repository.AnimalsRepository
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.domain.GrantBuildingToAnimalUseCase
import javax.inject.Inject

class AnimalViewModel @Inject constructor(
    animalsRepository: AnimalsRepository,
    mapsRepository: MapsRepository,
    val grantBuildingToAnimalUseCase: GrantBuildingToAnimalUseCase,
    animalId: String
) : ViewModel() {
    val animal = animalsRepository.getAnimalById(animalId)

    val mapsWithUninhabitedBuildings = mapsRepository.getMapsWithUninhabitedBuildings()

    fun saveAnimalInBuilding(buildingId: String, animalId: String) {
        viewModelScope.launch (Dispatchers.IO) {
            grantBuildingToAnimalUseCase(buildingId, animalId)
        }
    }
}