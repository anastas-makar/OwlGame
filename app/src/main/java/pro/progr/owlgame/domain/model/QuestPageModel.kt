package pro.progr.owlgame.domain.model

data class QuestPageModel(
    val number: Int,
    val name: String? = null,
    val description: String,
    val imageUrl: String,
    val options: List<QuestOptionModel> = emptyList(),
    val endingId: String? = null,


    val scenePatch: QuestScenePatchModel? = null,
    val lootAvailable: Boolean = false,
    val lootButtonText: String? = null,
    val imageKey: String
)