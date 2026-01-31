package pro.progr.owlgame.data.preferences

import android.content.Context
import javax.inject.Inject

class OwlPreferences @Inject constructor(
    context: Context
) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getAnimalId(): String? = prefs.getString(ANIMAL_ID, null)

    fun setAnimalId(id: String) {
        prefs.edit().putString(ANIMAL_ID, id).apply()
    }

    fun getCachedCardIndex(): Int? = prefs.getInt(CACHED_CARD_INDEX, -1).takeIf { it != -1 }

    fun setCachedCardIndex(index: Int) {
        prefs.edit().putInt(CACHED_CARD_INDEX, index).apply()
    }

    fun getLastCardDay(): Long = prefs.getLong(LAST_CARD_DAY, -1L)

    fun setLastCardDay(epochDay: Long) {
        prefs.edit().putLong(LAST_CARD_DAY, epochDay).apply()
    }

    fun getLastPouchOpenDay(): Long = prefs.getLong(LAST_POUCH_DAY, -1L)

    fun setLastPouchOpenDay(epochDay: Long) {
        prefs.edit().putLong(LAST_POUCH_DAY, epochDay).apply()
    }

    fun getGrowthUpdate() : Long = prefs.getLong(GROWTH_UPDATE, 0L)

    fun setGrowthUpdate(updateTime: Long) {
        prefs.edit().putLong(GROWTH_UPDATE, updateTime).apply()
    }
}