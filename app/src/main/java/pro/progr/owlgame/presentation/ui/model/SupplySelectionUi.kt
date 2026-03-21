package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.data.db.Supply

data class SupplySelectionUi(
    val supply: Supply,
    val selectedAmount: Int
)
