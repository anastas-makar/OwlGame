package pro.progr.owlgame.data.web.quest

data class QuestApiModel(
    val questId: String,
    val title: String? = null,
    val startPageNumber: Int = 0,
    val pages: List<QuestPageApiModel>
)

