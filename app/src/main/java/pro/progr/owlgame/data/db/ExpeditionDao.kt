package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pro.progr.owlgame.data.model.ExpeditionStatus

@Dao
interface ExpeditionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(expeditions: List<Expedition>): List<Long>

    @Query("""
        UPDATE expeditions
        SET healAmount = :healAmount,
            damageAmount = :damageAmount,
            status = :status
        WHERE id = :expeditionId
    """)
    suspend fun updateStartData(
        expeditionId: String,
        healAmount: Int,
        damageAmount: Int,
        status: ExpeditionStatus
    ): Int
}