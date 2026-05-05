package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel

data class FreeMapLootUiState(
    val expedition: ExpeditionWithDataModel? = null,
    val animal: AnimalModel? = null
) {
    val shouldShowVictoryDialog: Boolean
        get() = expedition != null && animal != null
}