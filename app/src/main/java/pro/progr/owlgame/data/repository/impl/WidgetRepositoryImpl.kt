package pro.progr.owlgame.data.repository.impl

import android.content.Context
import android.net.Uri
import pro.progr.owlgame.data.db.dao.AnimalDao
import pro.progr.owlgame.data.db.dao.MapDao
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.data.preferences.OwlPreferences
import pro.progr.owlgame.domain.repository.WidgetRepository
import pro.progr.owlgame.data.util.UriWrapper
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.MapModel
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

private const val NO_DAY = -1L
private const val MAX_FUTURE_DAYS = 4L
private const val MISSED_DAYS_LIMIT = 2L

class WidgetRepositoryImpl @Inject constructor(
    private val preferences: OwlPreferences,
    private val animalDao: AnimalDao,
    private val mapDao: MapDao,
    private val context: Context,
    private val clock: Clock
) :  WidgetRepository {
    override fun getRandomMap(): MapModel? {
        return mapDao.getRandomMap()?.toDomain()
    }

    override fun getAnimal(): AnimalModel? {
        val animalId = preferences.getAnimalId()
        if (animalId != null) {
            return animalDao.getAnimalById(animalId)?.toDomain()
        }

        return null
    }

    override fun isPouchAvailable(): Boolean {
        val today = LocalDate.now(clock).toEpochDay()
        val lastPouchDay = preferences.getLastPouchOpenDay()

        if (lastPouchDay == NO_DAY) {
            return true
        }

        if (lastPouchDay > today) {
            // Часы устройства когда-то были выставлены в будущее.
            // Не оставляем пользователя без мешочков на месяцы/годы,
            // но и не выдаём дополнительный мешочек прямо сейчас.
            preferences.setLastPouchOpenDay(today)
            return false
        }

        return lastPouchDay < today
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

    override fun isMerchantAvailable(): Boolean {
        val today = LocalDate.now(clock).toEpochDay()
        val storedDay = preferences.getNextMerchantDay()

        val nextMerchantDay = normalizeMerchantDay(
            storedDay = storedDay,
            today = today
        )

        return nextMerchantDay == today
    }

    override fun markMerchantOpened() {
        val today = LocalDate.now(clock).toEpochDay()
        preferences.setNextMerchantDay(today + merchantDelayDays())
    }

    private fun normalizeMerchantDay(
        storedDay: Long,
        today: Long
    ): Long {
        if (storedDay == NO_DAY) {
            preferences.setNextMerchantDay(today)
            return today
        }

        if (storedDay > today + MAX_FUTURE_DAYS) {
            preferences.setNextMerchantDay(today)
            return today
        }

        if (storedDay < today - MISSED_DAYS_LIMIT) {
            preferences.setNextMerchantDay(today)
            return today
        }

        if (storedDay == today) {
            return today
        }

        if (storedDay > today) {
            return storedDay
        }

        // Вчера или позавчера: торговец был пропущен,
        // двигаем следующую дату от дня, когда он был доступен.
        val newDay = storedDay + merchantDelayDays()
        preferences.setNextMerchantDay(newDay)

        return newDay
    }

    private fun merchantDelayDays(): Long {
        return if (kotlin.random.Random.nextBoolean()) 2L else 3L
    }
}
