package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipts")
data class Ingredient (
    @PrimaryKey
    val id : String,
    val receiptId : String,
    val supplyName : String,
    val supplyAmount : Int
)