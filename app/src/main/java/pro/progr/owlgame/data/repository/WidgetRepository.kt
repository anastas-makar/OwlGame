package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.db.AnimalDao
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.preferences.OwlPreferences
import java.time.LocalDate
import javax.inject.Inject

class WidgetRepository @Inject constructor(
    private val preferences: OwlPreferences,
    private val animalDao: AnimalDao,
    private val mapDao: MapDao
) {
    fun getRandomMap() : MapEntity? {
        TODO()
    }

    fun getAnimal() : Flow<Animal?> {
                    val animalId = preferences.getAnimalId()
            if (animalId !=null) {
                return animalDao.getById(animalId)
            }

        return flowOf(null)
    }

    fun isPouchAvailable() : Boolean {
        val lastPouchDay = preferences.getLastPouchOpenDay()
        return lastPouchDay < LocalDate.now().toEpochDay()
    }
}