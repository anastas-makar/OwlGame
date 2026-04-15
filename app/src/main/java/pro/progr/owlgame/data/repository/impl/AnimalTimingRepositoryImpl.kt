package pro.progr.owlgame.data.repository.impl

import android.content.Context
import pro.progr.owlgame.data.preferences.ANIMAL_DAY
import pro.progr.owlgame.data.preferences.ANIMAL_ID
import pro.progr.owlgame.data.preferences.PREFS_NAME
import pro.progr.owlgame.domain.repository.AnimalTimingRepository
import javax.inject.Inject
import androidx.core.content.edit

class AnimalTimingRepositoryImpl @Inject constructor(
    context: Context
) : AnimalTimingRepository {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override fun clearAnimalDayAndId() {
        prefs.edit {
            remove(ANIMAL_DAY)
                .remove(ANIMAL_ID)
        }
    }
}