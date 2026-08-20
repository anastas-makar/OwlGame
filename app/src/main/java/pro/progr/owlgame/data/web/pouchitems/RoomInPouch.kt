package pro.progr.owlgame.data.web.pouchitems

data class RoomInPouch (
    val id : String,
    val name : String,
    val imageUrl : String,
    val roomNumber : Int,
    val templateId: String? = null,
    val imageKey: String? = null
)