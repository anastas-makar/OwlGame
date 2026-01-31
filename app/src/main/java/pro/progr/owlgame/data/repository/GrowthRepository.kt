package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.preferences.OwlPreferences
import javax.inject.Inject

private const val GROW_DURATION_MILLIS = 2L * 24 * 60 * 60 * 1000 // 2 дня
private const val COOLING_PERIOD = 30 * 60 * 1000 // полчаса

class GrowthRepository @Inject constructor(
    private val preferences: OwlPreferences) {

    private fun shouldGrow(lastUpdate: Long, now: Long) : Boolean {

        return now - lastUpdate > COOLING_PERIOD
    }

    private fun getDelta(lastUpdate: Long, now: Long) : Float {
        return (now.toFloat() - lastUpdate.toFloat()) / GROW_DURATION_MILLIS.toFloat()
    }

    fun setGrowthUpdate(updateTime: Long) {
        preferences.setGrowthUpdate(updateTime)
    }

    fun getGrowthState() : GrowthState {
        val last = preferences.getGrowthUpdate()
        val now = System.currentTimeMillis()

        if (shouldGrow(last, now)) {
            return GrowthState.Growing(getDelta(last, now))
        }

        return GrowthState.Suspended()
    }
}

