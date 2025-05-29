package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(animal: Animal): Long
    @Query("SELECT COUNT(*) FROM animals WHERE status='SEARCHING'")
    fun countSearching(): Long

    @Query("UPDATE animals SET status='PET' WHERE id=:animalId")
    fun setPet(animalId: String)

    @Query("UPDATE animals SET status='GONE' WHERE id=:animalId")
    fun setGone(animalId: String)

    @Query("SELECT * FROM animals WHERE id=:id")
    fun getById(id: String): Flow<Animal?>

    @Query("SELECT * FROM animals WHERE id=:id")
    fun getAnimalById(id: String): Animal?
}