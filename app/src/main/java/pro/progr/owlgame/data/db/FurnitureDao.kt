package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
public interface FurnitureDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(furniture: List<Furniture>): List<Long>

    @Query("UPDATE furniture SET roomId=:roomId " +
            " WHERE id=:itemId")
    fun setToRoom(itemId: String, roomId: String): Int

    @Query("UPDATE furniture SET x=:x, y=:y WHERE id=:itemId")
    fun updatePosition(itemId: String, x: Float, y: Float): Int

    @Query("SELECT * FROM furniture WHERE roomId IS NULL")
    fun getAvailable() : Flow<List<Furniture>>

    @Query("SELECT * FROM furniture WHERE roomId=:roomId")
    fun observeByRoomId(roomId : String) : Flow<List<Furniture>>
}