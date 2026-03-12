package pro.progr.owlgame.data.web.inpouch

data class ExpeditionInPouch(
    val id: String,
    val title: String,
    val description: String,
    val enemies: List<EnemyInPouch>
)