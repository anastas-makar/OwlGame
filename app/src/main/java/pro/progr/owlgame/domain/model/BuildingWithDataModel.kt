package pro.progr.owlgame.domain.model

data class BuildingWithDataModel(
    val id : String,
    val name : String,
    val imageUrl : String,
    val mapId: String?,
    val price : Int = 500,
    val x : Float = 0f,
    val y : Float = 0f,
    val type: BuildingType,
    var animal: AnimalModel? = null,
    val rooms: List<RoomModel>,
    val gardens: List<GardenModel>
)
