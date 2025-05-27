package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.preferences.OwlPreferences
import java.time.LocalDate
import javax.inject.Inject

class WidgetRepository @Inject constructor(
    private val preferences: OwlPreferences,
    private val mapDao: MapDao
) {
    fun getRandomMap() : MapEntity? {
        TODO()
    }

    fun getAnimal() : Animal? {
        TODO()
    }

    fun isPouchAvailable() : Boolean {
        val lastPouchDay = preferences.getLastPouchOpenDay()
        return lastPouchDay < LocalDate.now().toEpochDay()
    }
}