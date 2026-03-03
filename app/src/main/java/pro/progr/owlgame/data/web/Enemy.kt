package pro.progr.owlgame.data.web

data class Enemy (
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val number: Int,
    val healAmount: Int,
    val attackAmount: Int
)