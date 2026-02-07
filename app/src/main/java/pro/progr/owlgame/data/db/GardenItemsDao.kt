package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
public interface GardenItemsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(gardenItems: List<GardenItem>): List<Long>

    @Query("UPDATE garden_items SET gardenId=:gardenId " +
            " WHERE id=:itemId")
    fun setToGarden(itemId: String, gardenId: String): Int

    @Query("UPDATE garden_items SET x=:x, y=:y WHERE id=:itemId")
    fun updatePosition(itemId: String, x: Float, y: Float): Int

    @Query("SELECT * FROM garden_items WHERE gardenId IS NULL AND gardenType = :gardenType")
    fun getAvailable(gardenType: GardenType) : Flow<List<GardenItem>>

    @Query("SELECT * FROM garden_items WHERE gardenId=:gardenId")
    fun observeByGardenId(gardenId : String) : Flow<List<GardenItem>>

    @Query("""
        UPDATE garden_items
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

    @Query("UPDATE garden_items SET readiness=0.0 WHERE id = :itemId")
    suspend fun flushReadinessForItem(itemId: String)
}