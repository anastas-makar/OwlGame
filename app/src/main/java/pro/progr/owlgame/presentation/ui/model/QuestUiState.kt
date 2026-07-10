package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.domain.model.QuestPageModel

data class QuestUiState(
    val isLoading: Boolean = true,
    val questTitle: String? = null,
    val currentPage: QuestPageModel? = null,
    val isCompleting: Boolean = false,
    val isCompleted: Boolean = false,
    val errorMessage: String? = null
)