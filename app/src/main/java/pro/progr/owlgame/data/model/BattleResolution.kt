package pro.progr.owlgame.data.model

import pro.progr.owlgame.data.db.entity.Enemy
import pro.progr.owlgame.data.db.model.AnimalStatus
import pro.progr.owlgame.data.db.model.MapType

data class BattleResolution(
    val expeditionHeal: Int,
    val expeditionDamage: Int,
    val updatedEnemies: List<Enemy>,
    val expeditionStatus: ExpeditionStatus,
    val mapType: MapType,
    val animalStatus: AnimalStatus,
    val appliedTicks: Long,
    val medalForAnimal: Boolean = false
)