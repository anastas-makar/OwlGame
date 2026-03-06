package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ExpeditionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(furniture: List<Expedition>): List<Long>
}