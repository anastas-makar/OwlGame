package pro.progr.owlgame.domain.model

data class QuestCompletionResultModel(
    val questId: String,
    val locationSceneId: String,
    val endingId: String,
    val scenePatch: QuestScenePatchModel,
    val lootAvailable: Boolean = false,
    val lootButtonText: String? = null
)

