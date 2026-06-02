package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import pro.progr.owlgame.data.db.entity.LocationScene

@Dao
interface LocationScenesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(locationScenes: List<LocationScene>): List<Long>
}