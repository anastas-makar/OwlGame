package pro.progr.owlgame.data.web.inpouch

data class EnemyInPouch (
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val healAmount: Int,
    val damageAmount: Int,
    val x: Float,
    val y: Float
)