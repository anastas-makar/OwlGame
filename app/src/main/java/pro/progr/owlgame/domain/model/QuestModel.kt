package pro.progr.owlgame.domain.model

data class QuestModel(
    val questId: String,
    val title: String? = null,
    val startPageNumber: Int = 0,
    val pages: List<QuestPageModel>,
    val templateId: String
)

