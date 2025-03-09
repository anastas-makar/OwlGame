package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnimalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(animal: Animal): Long
    @Query("SELECT COUNT(*) FROM animals WHERE status='SEARCHING'")
    fun countSearching(): Long
}