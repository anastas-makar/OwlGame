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
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.domain.model.InPouchModel
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import pro.progr.owlgame.domain.repository.LootRepository
import pro.progr.owlgame.domain.usecase.SavePouchUseCase
import pro.progr.owlgame.presentation.ui.model.FreeMapLootUiState
import javax.inject.Inject


class FreeMapViewModel @Inject constructor(
    private val expeditionsRepository: ExpeditionsRepository,
    private val animalsRepository: AnimalsRepository,
    private val lootRepository: LootRepository,
    private val savePouchUseCase: SavePouchUseCase,
    private val mapId: String
) : ViewModel() {

    private val isClaimingLoot = MutableStateFlow(false)
    private val claimedLoot = MutableStateFlow<InPouchModel?>(null)
    private val errorMessage = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val baseLootState: Flow<FreeMapLootUiState> =
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

    val lootUiState: StateFlow<FreeMapLootUiState> =
        combine(
            baseLootState,
            isClaimingLoot,
            claimedLoot,
            errorMessage
        ) { base, claiming, loot, error ->
            base.copy(
                isClaimingLoot = claiming,
                claimedLoot = loot,
                errorMessage = error
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FreeMapLootUiState()
        )

    fun exploreDungeon(expeditionId: String, diamondDao: PurchaseInterface) {
        viewModelScope.launch(Dispatchers.IO) {
            isClaimingLoot.value = true
            errorMessage.value = null

            try {
                val lootResult = lootRepository.claimExpeditionLoot(expeditionId)

                if (lootResult.isFailure) {
                    errorMessage.value = lootResult.exceptionOrNull()?.message
                        ?: "Не удалось получить лут"
                    return@launch
                }

                val loot = lootResult.getOrThrow()
                val savedLoot = savePouchUseCase(loot, diamondDao)

                val markResult = expeditionsRepository.markLootClaimed(expeditionId)
                if (markResult.isFailure) {
                    errorMessage.value = markResult.exceptionOrNull()?.message
                        ?: "Лут сохранён, но экспедиция не закрыта"
                    return@launch
                }

                claimedLoot.value = savedLoot
            } finally {
                isClaimingLoot.value = false
            }
        }
    }

    fun winWithoutLoot(expeditionId: String) {
        viewModelScope.launch (Dispatchers.IO) {
            val markResult = expeditionsRepository.markLootClaimed(expeditionId)
            if (markResult.isFailure) {
                errorMessage.value = markResult.exceptionOrNull()?.message
                    ?: "Не удаётся закрыть экспедицию"
                return@launch
            }
        }
    }

    fun closeClaimedLootDialog() {
        claimedLoot.value = null
    }

    fun clearError() {
        errorMessage.value = null
    }
}