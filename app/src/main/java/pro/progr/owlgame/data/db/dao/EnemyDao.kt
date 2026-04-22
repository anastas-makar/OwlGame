package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import pro.progr.owlgame.data.db.entity.Enemy

@Dao
interface EnemyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(enemies: List<Enemy>): List<Long>

    @Update
    suspend fun updateEnemies(enemies: List<Enemy>)
}