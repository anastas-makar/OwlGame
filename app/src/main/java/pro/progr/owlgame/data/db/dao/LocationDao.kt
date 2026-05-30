package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import pro.progr.owlgame.data.db.entity.Location

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(locations: List<Location>): List<Long>
}