package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.db.AnimalDao
import javax.inject.Inject

class AnimalsRepository @Inject constructor(
    private val animalDao: AnimalDao
) {
    fun countAnimalsSearching() : Long {
        return animalDao.countSearching()
    }
}