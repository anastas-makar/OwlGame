package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
public interface GardenItemsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(gardenItems: List<GardenItem>): List<Long>
}