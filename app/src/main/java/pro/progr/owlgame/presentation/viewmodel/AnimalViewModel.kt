package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.BuildingsRepository
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.domain.usecase.GrantBuildingToAnimalUseCase
import pro.progr.owlgame.domain.model.MapWithDataModel
import javax.inject.Inject

class AnimalViewModel @Inject constructor(
    animalsRepository: AnimalsRepository,
    mapsRepository: MapsRepository,
    buildingsRepository: BuildingsRepository,
    val grantBuildingToAnimalUseCase: GrantBuildingToAnimalUseCase,
    animalId: String
) : ViewModel() {
    val animal = animalsRepository.getAnimalById(animalId)

    val mapsWithUninhabitedBuildings: StateFlow<List<MapWithDataModel>> = combine(
        mapsRepository.getMapsWithUninhabitedBuildings(),
        buildingsRepository.getBuildingsWithAnimals()
    ) { mapsWithData, buildingsMap ->

        mapsWithData.map { mapWithData ->
            val mapId = mapWithData.mapEntity.id

            MapWithDataModel(
                id = mapId,
                name = mapWithData.mapEntity.name,
                imageUrl = mapWithData.mapEntity.imagePath,
                type = mapWithData.mapEntity.type,
                buildings = buildingsMap.values
                    .filter { it.building.mapId == mapId }
                    .toList()
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    fun saveAnimalInBuilding(buildingId: String, animalId: String) {
        viewModelScope.launch (Dispatchers.IO) {
            grantBuildingToAnimalUseCase(buildingId, animalId)
        }
    }
}