package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import pro.progr.owlgame.data.db.entity.Outbox

@Dao
interface OutboxDao {
    @Query("SELECT * FROM outbox ORDER BY id")
    suspend fun getSync(): List<Outbox>

    @Delete
    suspend fun clearSync(outboxes: List<Outbox>)

    @Query("DELETE FROM outbox")
    suspend fun clearAll()
}
