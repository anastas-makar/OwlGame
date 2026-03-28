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

    @Query("SELECT * FROM animals WHERE status='SEARCHING' LIMIT 1")
    fun getSearchingAnimal(): Animal?

    @Query("UPDATE animals SET status='PET' WHERE id=:animalId")
    fun setPet(animalId: String)

    @Query("UPDATE animals SET status='GONE' WHERE id=:animalId")
    fun setGone(animalId: String)

    @Query("SELECT * FROM animals WHERE id=:id")
    fun getById(id: String): Flow<Animal?>

    @Query("SELECT * FROM animals WHERE id=:id")
    fun getAnimalById(id: String): Animal?

    @Query("SELECT * FROM animals WHERE status = :status")
    fun getAnimalsByStatus(status: AnimalStatus): Flow<List<Animal>>

    @Query("SELECT * FROM animals WHERE status = :status")
    suspend fun getAnimalsByStatusOnce(status: AnimalStatus): List<Animal>

    @Query("""
        UPDATE animals
        SET status = :newStatus
        WHERE id = :animalId AND status = :expectedOldStatus
    """)
    suspend fun updateStatusIfCurrent(
        animalId: String,
        newStatus: AnimalStatus,
        expectedOldStatus: AnimalStatus
    ): Int
}