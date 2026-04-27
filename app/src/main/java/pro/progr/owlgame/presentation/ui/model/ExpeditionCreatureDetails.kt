package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.EnemyModel
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel

sealed interface ExpeditionCreatureDetails {
    data class AnimalDetails(
        val animal: AnimalModel,
        val expedition: ExpeditionWithDataModel
    ) : ExpeditionCreatureDetails

    data class EnemyDetails(
        val enemy: EnemyModel
    ) : ExpeditionCreatureDetails
}