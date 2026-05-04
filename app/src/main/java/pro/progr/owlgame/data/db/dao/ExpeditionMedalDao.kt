package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pro.progr.owlgame.data.db.entity.ExpeditionMedal

@Dao
interface ExpeditionMedalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(expeditionMedals: List<ExpeditionMedal>): List<Long>

    @Query("UPDATE expedition_medals SET animalId=:animalId WHERE expeditionId=:expeditionId")
    fun updateAnimalId(animalId: String, expeditionId: String)
}