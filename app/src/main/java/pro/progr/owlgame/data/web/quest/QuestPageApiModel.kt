package pro.progr.owlgame.data.web.quest

data class QuestPageApiModel(
    val number: Int,
    val name: String? = null,
    val description: String,
    val imageUrl: String,
    val options: List<QuestOptionApiModel> = emptyList(),

    val endingId: String? = null,
    val scenePatch: QuestScenePatchApiModel? = null,
    val lootAvailable: Boolean = false,
    val lootButtonText: String? = null,
    val imageKey: String
)