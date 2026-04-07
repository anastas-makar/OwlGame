package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import pro.progr.owlgame.data.db.entity.Street

@Dao
interface StreetsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(street: Street): Long
}
