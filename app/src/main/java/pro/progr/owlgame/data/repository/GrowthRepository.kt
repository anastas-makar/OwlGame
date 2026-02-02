package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.preferences.OwlPreferences
import javax.inject.Inject

private const val GROW_DURATION_MILLIS = 2L * 24 * 60 * 60 * 1000 // 2 дня
private const val COOLING_PERIOD = 30 * 60 * 1000 // полчаса

private const val FUTURE_TOLERANCE = 1L * 60 * 1000 //допуск при проверке времени

class GrowthRepository @Inject constructor(
    private val preferences: OwlPreferences) {

    private fun shouldGrow(lastUpdate: Long, now: Long) : Boolean {

        return now - lastUpdate > COOLING_PERIOD
    }

    private fun needsStart(last: Long, now: Long) : Boolean {
        return last == 0L || last > now + FUTURE_TOLERANCE
    }

    private fun getDelta(lastUpdate: Long, now: Long) : Float {
        return (now - lastUpdate).toFloat() / GROW_DURATION_MILLIS.toFloat()
    }

    fun setGrowthUpdate(updateTime: Long) {
        preferences.setGrowthUpdate(updateTime)
    }

    fun getGrowthState() : GrowthState {
        val last = preferences.getGrowthUpdate()

        val now = System.currentTimeMillis()

        if (needsStart(last, now)) {
            return GrowthState.NeedsStart(now)
        }

        if (shouldGrow(last, now)) {
            return GrowthState.Growing(getDelta(last, now), now)
        }

        return GrowthState.Suspended()
    }
}

