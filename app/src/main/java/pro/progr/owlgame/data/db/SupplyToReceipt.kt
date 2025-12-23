package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.temporal.TemporalAmount

@Entity(tableName = "supply_to_receipt")
data class SupplyToReceipt (
    @PrimaryKey
    val id : String,
    val supplyId : String,
    val receiptId : String,
    val amount: Int
)