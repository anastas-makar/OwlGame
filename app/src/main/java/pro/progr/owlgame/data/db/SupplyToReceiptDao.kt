package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SupplyToReceiptDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<SupplyToReceipt>)

    @Query("DELETE FROM supply_to_receipt WHERE receiptId IN (:receiptIds)")
    suspend fun deleteByReceiptIds(receiptIds: List<String>)
}