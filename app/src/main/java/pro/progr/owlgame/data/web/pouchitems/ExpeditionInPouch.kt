package pro.progr.owlgame.data.web.pouchitems

data class ExpeditionInPouch(
    val id: String,
    val title: String,
    val description: String,
    val enemies: List<EnemyInPouch>,
    val medal: ExpeditionMedalInPouch
)