package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
public interface PlantsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(buildings: List<Plant>): List<Long>
}