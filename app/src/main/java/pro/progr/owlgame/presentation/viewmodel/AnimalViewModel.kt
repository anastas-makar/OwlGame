package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.BuildingsRepository
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.domain.usecase.GrantBuildingToAnimalUseCase
import pro.progr.owlgame.domain.model.MapWithDataModel
import pro.progr.owlgame.domain.usecase.SendAnimalAwayUseCase
import javax.inject.Inject

sealed interface AnimalSearchingEvent {
    data class OpenMap(val mapId: String) : AnimalSearchingEvent
}

class AnimalViewModel @Inject constructor(
    private val animalsRepository: AnimalsRepository,
    mapsRepository: MapsRepository,
    buildingsRepository: BuildingsRepository,
    private val grantBuildingToAnimalUseCase: GrantBuildingToAnimalUseCase,
    private val sendAnimalAwayUseCase: SendAnimalAwayUseCase,
    private val animalId: String
) : ViewModel() {

    val animal = animalsRepository.getAnimalById(animalId)

    private val _events = Channel<AnimalSearchingEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    private val _isBusy = MutableStateFlow(false)
    val isBusy: StateFlow<Boolean> = _isBusy

    val mapsWithFreeBuildings: StateFlow<List<MapWithDataModel>> = combine(
        mapsRepository.getMapsWithUninhabitedBuildings(),
        buildingsRepository.getBuildingsWithAnimals()
    ) { mapsWithData, buildingsMap ->

        mapsWithData.mapNotNull { mapWithData ->
            val freeBuildings = buildingsMap.values
                .filter { building ->
                    building.mapId == mapWithData.id && building.animal == null
                }

            if (freeBuildings.isEmpty()) {
                null
            } else {
                MapWithDataModel(
                    id = mapWithData.id,
                    name = mapWithData.name,
                    imageUrl = mapWithData.imageUrl,
                    type = mapWithData.type,
                    mayorAnimalId = mapWithData.mayorAnimalId,
                    buildings = freeBuildings
                )
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun settleAnimalInBuilding(
        buildingId: String,
        mapId: String,
        newAnimalName: String
    ) {
        val trimmedName = newAnimalName.trim()
        if (trimmedName.isBlank()) return

        viewModelScope.launch {
            _isBusy.value = true

            try {
                withContext(Dispatchers.IO) {
                    grantBuildingToAnimalUseCase(
                        buildingId = buildingId,
                        animalId = animalId,
                        newAnimalName = trimmedName
                    )
                }

                _events.send(AnimalSearchingEvent.OpenMap(mapId))
            } finally {
                _isBusy.value = false
            }
        }
    }

    fun sendAnimalAway() {
        viewModelScope.launch {
            _isBusy.value = true

            try {
                withContext(Dispatchers.IO) {
                    sendAnimalAwayUseCase(animalId)
                }
            } finally {
                _isBusy.value = false
            }
        }
    }
}