package pro.progr.owlgame.domain.model

data class StartExpeditionRequest(
    val mapId: String,
    val expeditionId: String,
    val animalId: String,
    val selectedSupplies: List<SelectedSupplyAmount>,
    val extraHeal: Int,
    val extraDamage: Int
)
