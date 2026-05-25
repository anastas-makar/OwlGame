package pro.progr.owlgame.domain.model

data class StreetModel (
    val id: String,
    val mapId: String,
    val name: String,
    val direction: StreetDirection = StreetDirection.WEST_TO_EAST)