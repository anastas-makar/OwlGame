package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
public interface TownsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(town: Town): Long
}
