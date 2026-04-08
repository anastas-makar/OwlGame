package pro.progr.owlgame.domain.model

data class EnemyModel (
    val id: String,
    val expeditionId: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val healAmount: Int,
    val damageAmount: Int,
    val x: Float,
    val y: Float,
    val status: EnemyStatus
)