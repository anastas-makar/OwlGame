package pro.progr.owlgame.data.repository

interface SlotsRepository {

    fun saveSlot(x : Float, y : Float, mapId : String, buildingId : String)

    fun updateSlot(buildingId : String, x : Float, y : Float)
}