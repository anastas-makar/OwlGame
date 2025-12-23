package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.data.db.BuildingWithAnimal

data class MapData (
    val id: String,
    val name: String,
    val imageUrl: String,
    val buildings: List<BuildingWithAnimal> = emptyList()
)