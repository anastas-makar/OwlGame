package pro.progr.owlgame.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.EnemyStatus
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import pro.progr.owlgame.presentation.ui.model.ExpeditionBattleUiState
import javax.inject.Inject

class ExpeditionScreenViewModel @Inject constructor(
    private val expeditionsRepository: ExpeditionsRepository,
    private val animalsRepository: AnimalsRepository,
    private val mapId: String
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<ExpeditionBattleUiState> =
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
                        val activeEnemy = exp.enemies.firstOrNull { it.status == EnemyStatus.ACTIVE }

                        ExpeditionBattleUiState(
                            expedition = exp,
                            animal = animal,
                            activeEnemy = activeEnemy,
                            enemies = exp.enemies
                        )
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ExpeditionBattleUiState()
            )
}