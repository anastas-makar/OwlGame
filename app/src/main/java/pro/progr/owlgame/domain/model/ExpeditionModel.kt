package pro.progr.owlgame.domain.model

data class ExpeditionModel(
    val id: String,
    val title: String,
    val description: String,
    val mapId: String,
    val animalId: String?,
    val healAmount: Int,
    val damageAmount: Int,
    val maxHealAmount: Int,
    val maxDamageAmount: Int,
    val status: ExpeditionStatus = ExpeditionStatus.ACTIVE
)
