package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StreetsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(street: Street): Long
}
