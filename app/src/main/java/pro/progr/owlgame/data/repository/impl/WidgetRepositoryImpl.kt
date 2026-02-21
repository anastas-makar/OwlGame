package pro.progr.owlgame.data.repository.impl

import android.content.Context
import android.net.Uri
import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.db.AnimalDao
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.preferences.OwlPreferences
import pro.progr.owlgame.data.repository.WidgetRepository
import pro.progr.owlgame.presentation.ui.model.UriWrapper
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

class WidgetRepositoryImpl @Inject constructor(
    private val preferences: OwlPreferences,
    private val animalDao: AnimalDao,
    private val mapDao: MapDao,
    private val context: Context,
    private val clock: Clock
) :  WidgetRepository {
    override fun getRandomMap(): MapEntity? {
        TODO()
    }

    override fun getAnimal(): Animal? {
        val animalId = preferences.getAnimalId()
        if (animalId != null) {
            return animalDao.getAnimalById(animalId)
        }

        return null
    }

    override fun isPouchAvailable(): Boolean {
        val lastPouchDay = preferences.getLastPouchOpenDay()
        return lastPouchDay < LocalDate.now(clock).toEpochDay()
    }

    override fun getUri(res : Int) : Uri {
        return UriWrapper(res, context).uri
    }

    override fun clearAnimalDayAndId() {
        preferences.clearAnimalDayAndId()
    }

    override fun getUri(path : String) : Uri {
        return UriWrapper(path).uri
    }
}