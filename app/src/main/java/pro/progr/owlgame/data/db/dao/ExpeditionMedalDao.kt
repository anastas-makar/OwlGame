package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.entity.ExpeditionMedal

@Dao
interface ExpeditionMedalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(expeditionMedals: List<ExpeditionMedal>): List<Long>

    @Query("UPDATE expedition_medals SET animalId=:animalId WHERE expeditionId=:expeditionId")
    fun updateAnimalId(animalId: String, expeditionId: String)

    @Query("""
    SELECT * FROM expedition_medals
    WHERE animalId = :animalId
    ORDER BY title DESC
""")
    fun getAnimalMedals(animalId: String): Flow<List<ExpeditionMedal>>
}