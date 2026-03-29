package pro.progr.owlgame.data.model

data class StartExpeditionRequest(
    val mapId: String,
    val expeditionId: String,
    val animalId: String,
    val selectedSupplies: List<SelectedSupplyAmount>,
    val extraHeal: Int,
    val extraDamage: Int
)
