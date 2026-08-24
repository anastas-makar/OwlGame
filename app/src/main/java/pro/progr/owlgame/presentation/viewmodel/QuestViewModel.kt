package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.domain.model.QuestOptionModel
import pro.progr.owlgame.domain.model.QuestPageModel
import pro.progr.owlgame.domain.repository.LocationsRepository
import pro.progr.owlgame.domain.repository.LootRepository
import pro.progr.owlgame.domain.repository.QuestsRepository
import pro.progr.owlgame.domain.usecase.SavePouchUseCase
import pro.progr.owlgame.presentation.ui.model.QuestRewardPrompt
import pro.progr.owlgame.presentation.ui.model.QuestUiState
import javax.inject.Inject

class QuestViewModel @Inject constructor(
    private val questsRepository: QuestsRepository,
    private val lootRepository: LootRepository,
    private val locationsRepository: LocationsRepository,
    private val savePouchUseCase: SavePouchUseCase,
    private val questId: String,
    private val locationSceneId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuestUiState())
    val uiState: StateFlow<QuestUiState> = _uiState.asStateFlow()

    private var pagesByNumber: Map<Int, QuestPageModel> = emptyMap()

    init {
        loadQuest()
    }

    private fun loadQuest() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = withContext(Dispatchers.IO) {
                questsRepository.loadQuest(questId)
            }

            result
                .onSuccess { quest ->
                    pagesByNumber = quest.pages.associateBy { it.number }

                    val startPage = pagesByNumber[quest.startPageNumber]
                        ?: quest.pages.minByOrNull { it.number }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            questTitle = quest.title,
                            currentPage = startPage,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Quest loading error"
                        )
                    }
                }
        }
    }

    fun chooseOption(option: QuestOptionModel) {
        val nextPage = pagesByNumber[option.targetPageNumber]

        if (nextPage == null) {
            _uiState.update {
                it.copy(errorMessage = "Quest page not found")
            }
            return
        }

        _uiState.update {
            it.copy(
                currentPage = nextPage,
                errorMessage = null
            )
        }
    }

    fun completeQuest() {
        val page = _uiState.value.currentPage ?: return
        val endingId = page.endingId

        if (endingId == null) {
            _uiState.update {
                it.copy(errorMessage = "Quest page is not final")
            }
            return
        }

        val scenePatch = page.scenePatch

        if (scenePatch == null) {
            _uiState.update {
                it.copy(errorMessage = "Quest ending has no scene patch")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(isCompleting = true, errorMessage = null)
            }

            try {
                withContext(Dispatchers.IO) {
                    locationsRepository.applyQuestResult(
                        locationSceneId = locationSceneId,
                        imageUrl = scenePatch.imageUrl,
                        imageKey = scenePatch.imageKey,
                        description = scenePatch.description
                    )
                }

                _uiState.update {
                    it.copy(
                        isCompleting = false,
                        isQuestCompleted = true,
                        rewardPrompt = if (page.lootAvailable) {
                            QuestRewardPrompt(
                                questId = questId,
                                endingId = endingId,
                                buttonText = page.lootButtonText
                            )
                        } else {
                            null
                        }
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isCompleting = false,
                        errorMessage = e.message ?: "Quest completion error"
                    )
                }
            }
        }
    }

    fun claimQuestLoot(diamondDao: PurchaseInterface) {
        val rewardPrompt = _uiState.value.rewardPrompt ?: return

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(isClaimingLoot = true, errorMessage = null)
            }

            try {
                val lootResult = lootRepository.claimQuestLoot(
                    questId = rewardPrompt.questId,
                    endingId = rewardPrompt.endingId
                )

                if (lootResult.isFailure) {
                    _uiState.update {
                        it.copy(
                            errorMessage = lootResult.exceptionOrNull()?.message
                                ?: "Не удалось получить лут"
                        )
                    }
                    return@launch
                }

                val loot = lootResult.getOrThrow()
                val savedLoot = savePouchUseCase(loot, diamondDao)

                _uiState.update {
                    it.copy(
                        claimedLoot = savedLoot,
                        rewardPrompt = null
                    )
                }
            } finally {
                _uiState.update {
                    it.copy(isClaimingLoot = false)
                }
            }
        }
    }

    fun closeClaimedLootDialog() {
        _uiState.update {
            it.copy(claimedLoot = null)
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}
