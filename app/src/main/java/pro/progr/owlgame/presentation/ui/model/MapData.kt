package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.data.db.BuildingWithAnimal
import pro.progr.owlgame.data.db.MapType

data class MapData (
    val id: String,
    val name: String,
    val imageUrl: String,
    val type: MapType,
    val buildings: List<BuildingWithAnimal> = emptyList()
)