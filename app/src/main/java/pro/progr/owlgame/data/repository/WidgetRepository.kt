package pro.progr.owlgame.data.repository

import android.content.Context
import android.net.Uri
import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.db.AnimalDao
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.preferences.OwlPreferences
import pro.progr.owlgame.presentation.ui.model.UriWrapper
import java.time.LocalDate
import javax.inject.Inject

class WidgetRepository @Inject constructor(
    private val preferences: OwlPreferences,
    private val animalDao: AnimalDao,
    private val mapDao: MapDao,
    private val context: Context
) {
    fun getRandomMap(): MapEntity? {
        TODO()
    }

    fun getAnimal(): Animal? {
        val animalId = preferences.getAnimalId()
        if (animalId != null) {
            return animalDao.getAnimalById(animalId)
        }

        return null
    }

    fun isPouchAvailable(): Boolean {
        val lastPouchDay = preferences.getLastPouchOpenDay()
        return lastPouchDay < LocalDate.now().toEpochDay()
    }

    fun getUri(res : Int) : Uri {
        return UriWrapper(res, context).uri
    }

    fun clearAnimalDayAndId() {
        preferences.clearAnimalDayAndId()
    }

    fun getUri(path : String) : Uri {
        return UriWrapper(path).uri
    }
}