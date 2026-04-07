package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pro.progr.owlgame.data.db.entity.Expedition
import pro.progr.owlgame.data.model.ExpeditionStatus

@Dao
interface ExpeditionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(expeditions: List<Expedition>): List<Long>


    @Query("SELECT * FROM expeditions WHERE id = :expeditionId LIMIT 1")
    suspend fun getById(expeditionId: String): Expedition?

    @Query("""
        UPDATE expeditions
        SET animalId = :animalId
        WHERE id = :expeditionId
    """)
    suspend fun updateAnimalId(
        expeditionId: String,
        animalId: String?
    ): Int

    @Query("""
        UPDATE expeditions
        SET healAmount = :healAmount,
            damageAmount = :damageAmount,
            animalId = :animalId,
            status = :status
        WHERE id = :expeditionId
    """)
    suspend fun updateStartData(
        expeditionId: String,
        healAmount: Int,
        damageAmount: Int,
        animalId: String,
        status: ExpeditionStatus
    ): Int
}