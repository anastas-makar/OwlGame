package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.data.model.ExpeditionStatus

data class ExpeditionModelUI(
    val id: String,
    val title: String,
    val description: String,
    val mapId: String,
    val animalId: String?,
    val healAmount: Int,
    val damageAmount: Int,
    val status: ExpeditionStatus = ExpeditionStatus.ACTIVE,
    val enemies: List<EnemyModelUI>)
