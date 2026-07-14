package pro.progr.owlgame.presentation.ui.model

data class QuestRewardPrompt(
    val questId: String,
    val endingId: String,
    val buttonText: String? = null
)