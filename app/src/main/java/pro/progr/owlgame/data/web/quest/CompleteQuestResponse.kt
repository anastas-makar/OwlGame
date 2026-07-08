package pro.progr.owlgame.data.web.quest

import pro.progr.owlgame.data.web.pouchitems.PouchItemsDto

data class CompleteQuestResponse(
    val scenePatch: QuestScenePatchApiModel,
    val loot: PouchItemsDto? = null
)