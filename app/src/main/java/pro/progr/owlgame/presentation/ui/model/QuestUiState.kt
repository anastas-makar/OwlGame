package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.domain.model.PouchItemsModel
import pro.progr.owlgame.domain.model.QuestPageModel

data class QuestUiState(
    val isLoading: Boolean = true,
    val questTitle: String? = null,
    val currentPage: QuestPageModel? = null,
    val isCompleting: Boolean = false,
    val isQuestCompleted: Boolean = false,
    val rewardPrompt: QuestRewardPrompt? = null,
    val isClaimingLoot: Boolean = false,
    val claimedLoot: PouchItemsModel? = null,
    val errorMessage: String? = null
)