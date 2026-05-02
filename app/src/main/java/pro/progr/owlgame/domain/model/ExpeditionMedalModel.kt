package pro.progr.owlgame.domain.model

data class ExpeditionMedalModel (
    val id: String,
    val expeditionId: String,
    val mapId: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val animalId: String?
)