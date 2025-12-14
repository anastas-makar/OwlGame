package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.data.db.BuildingWithAnimal
import pro.progr.owlgame.data.db.Town

data class MapData (
    val id: String,
    val name: String,
    val imageUrl: String,
    val town: Town? = null,
    val buildings: List<BuildingWithAnimal> = emptyList()
)