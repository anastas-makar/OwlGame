package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.progr.owlgame.domain.model.EnemyStatus
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import pro.progr.owlgame.presentation.ui.model.ExpeditionFailureReason
import pro.progr.owlgame.presentation.ui.model.OccupiedMapFailureUiState
import javax.inject.Inject

class OccupiedMapViewModel @Inject constructor(
    private val expeditionsRepository: ExpeditionsRepository,
    private val animalsRepository: AnimalsRepository,
    private val mapId: String
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val errorMessage = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val baseState: Flow<OccupiedMapFailureUiState> =
        expeditionsRepository.getLatestLostExpedition(mapId)
            .flatMapLatest { expedition ->
                if (expedition?.animalId == null) {
                    flowOf(OccupiedMapFailureUiState())
                } else {
                    animalsRepository.getAnimalById(expedition.animalId)
                        .map { animal ->
                            val enemy = expedition.enemies
                                .firstOrNull { it.status == EnemyStatus.ACTIVE }
                                ?: expedition.enemies.firstOrNull { it.status != EnemyStatus.DEFEATED }

                            OccupiedMapFailureUiState(
                                lostExpedition = expedition,
                                animal = animal,
                                enemy = enemy,
                                reason = ExpeditionFailureReason.DEFEAT
                            )
                        }
                }
            }

    val uiState: StateFlow<OccupiedMapFailureUiState> =
        combine(baseState, isLoading, errorMessage) { base, loading, error ->
            base.copy(
                isLoading = loading,
                errorMessage = error
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = OccupiedMapFailureUiState()
        )

    fun regroupEnemies() {
        val expedition = uiState.value.lostExpedition ?: return

        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            errorMessage.value = null

            try {
                val result = expeditionsRepository.regroupEnemies(
                    mapId = mapId,
                    oldExpeditionId = expedition.id
                )

                if (result.isFailure) {
                    errorMessage.value = result.exceptionOrNull()?.message
                        ?: "Не удалось загрузить новую экспедицию"
                }
            } finally {
                isLoading.value = false
            }
        }
    }

    fun clearError() {
        errorMessage.value = null
    }
}