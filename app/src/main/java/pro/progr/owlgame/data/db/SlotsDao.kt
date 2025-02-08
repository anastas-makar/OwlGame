package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
public interface SlotsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(slot: Slot): Long
    @Update
    fun update(slot: Slot)
}
