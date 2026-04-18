package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.domain.model.SupplyModel

data class SupplySelectionUi(
    val supply: SupplyModel,
    val selectedAmount: Int
)
