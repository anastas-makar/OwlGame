package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.Town

data class MapData (
    val id: String,
    val name: String,
    val imageUrl: String,
    val town: Town? = null,
    val buildings: List<Building> = emptyList()
)