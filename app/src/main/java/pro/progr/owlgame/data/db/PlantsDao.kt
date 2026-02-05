package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
public interface PlantsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(plants: List<Plant>): List<Long>

    @Query("UPDATE plants SET gardenId=:gardenId " +
            " WHERE id=:itemId")
    fun setToGarden(itemId: String, gardenId: String): Int

    @Query("""
        UPDATE plants
        SET readiness =
          CASE
            WHEN readiness + :delta >= 1.0 THEN 1.0
            WHEN readiness + :delta <= 0.0 THEN 0.0
            ELSE readiness + :delta
          END
        WHERE readiness < 1.0
          AND gardenId IS NOT NULL
        """)
    suspend fun addReadinessToAllPlanted(delta: Float): Int

    @Query("UPDATE plants SET x=:x, y=:y WHERE id=:plantId")
    fun updatePosition(plantId: String, x: Float, y: Float): Int

    @Query("SELECT * FROM plants WHERE gardenId IS NULL")
    fun getAvailable() : Flow<List<Plant>>

    @Query("SELECT * FROM plants WHERE gardenId=:gardenId")
    fun observeByGardenId(gardenId : String) : Flow<List<Plant>>

    @Query("UPDATE plants SET deleted = 1 WHERE id = :id")
    suspend fun markDeleted(id: String)
}