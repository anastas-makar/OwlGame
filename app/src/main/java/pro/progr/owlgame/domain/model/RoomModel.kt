package pro.progr.owlgame.domain.model

data class RoomModel(
    val id : String,
    val name : String,
    val imageUrl : String,
    val buildingId : String,
    val roomNumber : Int
)
