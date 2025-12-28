package pro.progr.owlgame.data.web.inpouch

import pro.progr.owlgame.data.db.BuildingType

data class BuildingInPouch (
    val id : String,
    val type: BuildingType,
    val cost: Int,
    val name: String,
    val imageUrl: String,
    val rooms: List<RoomInPouch>,
    val gardens: List<GardenInPouch>
)