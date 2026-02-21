package pro.progr.owlgame.data.repository

interface StreetsRepository {

    fun createStreet(mapId : String, name : String) : String

}

