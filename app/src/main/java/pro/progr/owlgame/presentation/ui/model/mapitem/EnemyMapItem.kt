package pro.progr.owlgame.presentation.ui.model.mapitem

import pro.progr.owlgame.domain.model.EnemyModel

data class EnemyMapItem(
    val enemy: EnemyModel
) : OccupiedMapItem {
    override val id: String = enemy.id
    override val x: Float = enemy.x
    override val y: Float = enemy.y
}
