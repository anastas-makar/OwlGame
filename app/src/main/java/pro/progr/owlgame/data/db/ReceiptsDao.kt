package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(receipts: List<Receipt>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(receipt: Receipt)

    @Query("SELECT * FROM receipts WHERE id = :receiptId")
    fun getById(receiptId: String): Flow<Receipt?>
}