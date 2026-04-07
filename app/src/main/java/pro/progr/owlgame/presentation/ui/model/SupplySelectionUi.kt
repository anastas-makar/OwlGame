package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.data.db.entity.Supply

data class SupplySelectionUi(
    val supply: Supply,
    val selectedAmount: Int
)
