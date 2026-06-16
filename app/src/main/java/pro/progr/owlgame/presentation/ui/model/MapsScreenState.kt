package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.domain.model.MapModel

data class MapsScreenState(
    val freeTowns: List<MapModel> = emptyList(),
    val countries: List<CountrySection> = emptyList(),
    val freeMaps: List<MapModel> = emptyList(),
    val occupiedMaps: List<MapModel> = emptyList()
)
