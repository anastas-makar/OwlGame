package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.db.AnimalDao
import pro.progr.owlgame.data.web.AnimalApiService
import javax.inject.Inject

class AnimalsRepository @Inject constructor(
    private val animalDao: AnimalDao,
    private val animalApiService: AnimalApiService
) {
    fun countAnimalsSearching() : Long {
        return animalDao.countSearching()
    }
}