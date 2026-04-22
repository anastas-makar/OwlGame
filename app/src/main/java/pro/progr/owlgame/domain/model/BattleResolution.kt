package pro.progr.owlgame.domain.model

data class BattleResolution(
    val expeditionHeal: Int,
    val expeditionDamage: Int,
    val updatedEnemies: List<EnemyModel>,
    val expeditionStatus: ExpeditionStatus,
    val mapType: MapType,
    val animalStatus: AnimalStatus,
    val appliedTicks: Long
)
