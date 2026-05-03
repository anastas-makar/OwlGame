package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.embedded.ExpeditionWithData
import pro.progr.owlgame.data.model.ExpeditionStatus

@Dao
interface ExpeditionWithDataDao {
    @Transaction
    @Query("SELECT * FROM expeditions WHERE mapId = :mapId AND status = :status LIMIT 1")
    fun getExpeditionWithData(mapId: String,
                              status: ExpeditionStatus = ExpeditionStatus.ACTIVE): Flow<ExpeditionWithData?>

    @Transaction
    @Query("SELECT * FROM expeditions WHERE id = :expeditionId LIMIT 1")
    suspend fun getExpeditionWithDataById(expeditionId: String): ExpeditionWithData?

    @Transaction
    @Query("""
    SELECT * FROM expeditions
    WHERE mapId = :mapId AND status = :status
    LIMIT 1
""")
    fun getExpeditionWithDataByStatus(
        mapId: String,
        status: ExpeditionStatus
    ): Flow<ExpeditionWithData?>
}