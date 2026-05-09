package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel
import pro.progr.owlgame.domain.model.InPouchModel

data class FreeMapLootUiState(
    val expedition: ExpeditionWithDataModel? = null,
    val animal: AnimalModel? = null,
    val isClaimingLoot: Boolean = false,
    val claimedLoot: InPouchModel? = null,
    val errorMessage: String? = null
) {
    val shouldShowVictoryDialog: Boolean
        get() = expedition != null && animal != null && claimedLoot == null
}