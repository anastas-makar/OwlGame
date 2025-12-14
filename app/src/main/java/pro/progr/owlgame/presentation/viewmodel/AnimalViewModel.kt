package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.repository.AnimalsRepository
import pro.progr.owlgame.data.repository.BuildingsRepository
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.domain.GrantBuildingToAnimalUseCase
import pro.progr.owlgame.presentation.ui.model.MapData
import javax.inject.Inject

class AnimalViewModel @Inject constructor(
    animalsRepository: AnimalsRepository,
    mapsRepository: MapsRepository,
    buildingsRepository: BuildingsRepository,
    val grantBuildingToAnimalUseCase: GrantBuildingToAnimalUseCase,
    animalId: String
) : ViewModel() {
    val animal = animalsRepository.getAnimalById(animalId)

    val mapsWithUninhabitedBuildings: StateFlow<List<MapData>> = combine(
        mapsRepository.getMapsWithUninhabitedBuildings(),
        buildingsRepository.getBuildingsWithAnimals()
    ) { mapsWithData, buildingsMap ->
        mapsWithData.map { mapWithData ->
            MapData(
                id = mapWithData.mapEntity.id,
                name = mapWithData.mapEntity.name,
                imageUrl = mapWithData.mapEntity.imagePath,
                town = mapWithData.town,
                buildings = buildingsMap.values.toList()
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList<MapData>()
    )
    fun saveAnimalInBuilding(buildingId: String, animalId: String) {
        viewModelScope.launch (Dispatchers.IO) {
            grantBuildingToAnimalUseCase(buildingId, animalId)
        }
    }
}