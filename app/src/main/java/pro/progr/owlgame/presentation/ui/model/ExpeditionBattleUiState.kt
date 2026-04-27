package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.EnemyModel
import pro.progr.owlgame.domain.model.ExpeditionWithDataModel

data class ExpeditionBattleUiState(
    val expedition: ExpeditionWithDataModel? = null,
    val animal: AnimalModel? = null,
    val activeEnemy: EnemyModel? = null,
    val enemies: List<EnemyModel> = emptyList()
)