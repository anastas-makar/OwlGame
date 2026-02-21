package pro.progr.owlgame.data.repository.impl

import pro.progr.owlgame.data.preferences.OwlPreferences
import pro.progr.owlgame.data.repository.GrowthRepository
import pro.progr.owlgame.data.repository.GrowthState
import java.time.Clock
import javax.inject.Inject

private const val GROW_DURATION_MILLIS = 2L * 24 * 60 * 60 * 1000 // 2 дня
private const val COOLING_PERIOD = 30L * 60 * 1000 // полчаса
private const val FUTURE_TOLERANCE = 1L * 60 * 1000 //допуск при проверке времени

class GrowthRepositoryImpl @Inject constructor(
    private val preferences: OwlPreferences,
    private val clock: Clock) : GrowthRepository {

    private fun shouldGrow(lastUpdate: Long, now: Long) : Boolean {

        return now - lastUpdate > COOLING_PERIOD
    }

    private fun needsStart(last: Long, now: Long) : Boolean {
        return last == 0L || last > now + FUTURE_TOLERANCE
    }

    private fun getDelta(lastUpdate: Long, now: Long) : Float {
        return (now - lastUpdate).toFloat() / GROW_DURATION_MILLIS.toFloat()
    }

    override fun setGrowthUpdate(updateTime: Long) {
        preferences.setGrowthUpdate(updateTime)
    }

    override fun getGrowthState() : GrowthState {
        val last = preferences.getGrowthUpdate()

        val now = clock.millis()

        if (needsStart(last, now)) {
            return GrowthState.NeedsStart(now)
        }

        if (shouldGrow(last, now)) {
            return GrowthState.Growing(getDelta(last, now), now)
        }

        return GrowthState.Suspended
    }
}

