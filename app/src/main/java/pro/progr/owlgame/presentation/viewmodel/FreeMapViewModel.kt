package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import pro.progr.owlgame.presentation.ui.model.FreeMapLootUiState
import javax.inject.Inject


class FreeMapViewModel @Inject constructor(
    private val expeditionsRepository: ExpeditionsRepository,
    private val animalsRepository: AnimalsRepository,
    private val mapId: String
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val lootUiState: StateFlow<FreeMapLootUiState> =
        expeditionsRepository.getLootAvailableExpedition(mapId)
            .flatMapLatest { expedition ->
                if (expedition?.animalId == null) {
                    flowOf(FreeMapLootUiState())
                } else {
                    animalsRepository.getAnimalById(expedition.animalId)
                        .map { animal ->
                            FreeMapLootUiState(
                                expedition = expedition,
                                animal = animal
                            )
                        }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FreeMapLootUiState()
            )

    fun exploreDungeon(expeditionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            expeditionsRepository.claimExpeditionLoot(expeditionId)
        }
    }
}