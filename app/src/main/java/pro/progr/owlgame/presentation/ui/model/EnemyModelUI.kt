package pro.progr.owlgame.presentation.ui.model

data class EnemyModelUI (
    val id: String,
    val expeditionId: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val healAmount: Int,
    val damageAmount: Int,
    val x: Float,
    val y: Float,
    val status: EnemyStatusUI
)