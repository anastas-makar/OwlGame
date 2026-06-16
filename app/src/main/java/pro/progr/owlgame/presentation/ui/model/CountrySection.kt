package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.domain.model.CountryModel
import pro.progr.owlgame.domain.model.MapModel

data class CountrySection(
    val country: CountryModel,
    val towns: List<MapModel>
)