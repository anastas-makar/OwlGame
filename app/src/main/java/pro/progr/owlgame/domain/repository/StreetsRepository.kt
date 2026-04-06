package pro.progr.owlgame.domain.repository

interface StreetsRepository {

    fun createStreet(mapId : String, name : String) : String

}

