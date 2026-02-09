package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supply_to_receipt")
data class SupplyToReceipt (
    @PrimaryKey
    val id : String,
    val supplyId : String,
    val receiptId : String,
    val amount: Int
)