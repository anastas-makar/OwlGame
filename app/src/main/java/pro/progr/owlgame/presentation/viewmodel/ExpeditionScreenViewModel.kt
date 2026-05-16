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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.EnemyStatus
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import pro.progr.owlgame.presentation.ui.model.ExpeditionBattleUiState
import javax.inject.Inject

class ExpeditionScreenViewModel @Inject constructor(
    private val expeditionsRepository: ExpeditionsRepository,
    private val animalsRepository: AnimalsRepository,
    private val mapId: String
) : ViewModel() {

    private val isEscaping = MutableStateFlow(false)
    private val errorMessage = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val battleBaseState: Flow<ExpeditionBattleUiState> =
        expeditionsRepository.getExpeditionWithData(mapId)
            .flatMapLatest { expedition ->
                if (expedition == null) {
                    flowOf(ExpeditionBattleUiState())
                } else {
                    val animalFlow: Flow<AnimalModel?> =
                        expedition.animalId?.let { animalId ->
                            animalsRepository.getAnimalById(animalId)
                        } ?: flowOf(null)

                    combine(
                        flowOf(expedition),
                        animalFlow
                    ) { exp, animal ->
                        val activeEnemy = exp.enemies
                            .firstOrNull { it.status == EnemyStatus.ACTIVE }

                        ExpeditionBattleUiState(
                            expedition = exp,
                            animal = animal,
                            activeEnemy = activeEnemy,
                            enemies = exp.enemies
                        )
                    }
                }
            }

    val uiState: StateFlow<ExpeditionBattleUiState> =
        combine(
            battleBaseState,
            isEscaping,
            errorMessage
        ) { base, escaping, error ->
            base.copy(
                isEscaping = escaping,
                errorMessage = error
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ExpeditionBattleUiState()
        )

    fun escapeExpedition(expeditionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isEscaping.value = true
            errorMessage.value = null

            try {
                val result = expeditionsRepository.escapeExpedition(expeditionId)

                if (result.isFailure) {
                    errorMessage.value = result.exceptionOrNull()?.message
                        ?: "Не удалось сбежать из экспедиции"
                }
            } finally {
                isEscaping.value = false
            }
        }
    }

    fun clearError() {
        errorMessage.value = null
    }
}