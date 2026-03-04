package pro.progr.owlgame.data.web

data class Enemy (
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val healAmount: Int,
    val damageAmount: Int,
    val x: Float,
    val y: Float
)