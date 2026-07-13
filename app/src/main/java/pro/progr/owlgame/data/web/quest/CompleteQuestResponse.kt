package pro.progr.owlgame.data.web.quest

data class CompleteQuestResponse(
    val questId: String? = null,
    val locationSceneId: String? = null,
    val endingId: String? = null,
    val scenePatch: QuestScenePatchApiModel? = null,
    val lootAvailable: Boolean = false,
    val lootButtonText: String? = null,
    val error: String? = null
)