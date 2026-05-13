package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.EnemyModel
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel

data class OccupiedMapFailureUiState(
    val lostExpedition: ExpeditionWithDataModel? = null,
    val animal: AnimalModel? = null,
    val enemy: EnemyModel? = null,
    val reason: ExpeditionFailureReason = ExpeditionFailureReason.DEFEAT,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val shouldShowDialog: Boolean
        get() = lostExpedition != null && animal != null
}