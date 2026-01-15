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

    @Query("UPDATE plants SET gardenId=:gardenId," +
            "x=:x, y=:y WHERE id=:itemId")
    fun setToGarden(itemId: String, gardenId: String, x: Float, y: Float): Int

    @Query("UPDATE plants SET x=:x, y=:y WHERE id=:plantId")
    fun updatePosition(plantId: String, x: Float, y: Float): Int

    @Query("SELECT * FROM plants WHERE gardenId IS NULL")
    fun getAvailable() : Flow<List<Plant>>

    @Query("SELECT * FROM plants WHERE gardenId=:gardenId")
    fun observeByGardenId(gardenId : String) : Flow<List<Plant>>
}