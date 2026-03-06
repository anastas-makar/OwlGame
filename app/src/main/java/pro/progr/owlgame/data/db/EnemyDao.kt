package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface EnemyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(furniture: List<Enemy>): List<Long>
}