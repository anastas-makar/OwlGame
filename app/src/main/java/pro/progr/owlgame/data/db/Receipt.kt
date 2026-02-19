package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receipts")
data class Receipt (
    @PrimaryKey
    val id : String,
    val resSupplyId : String,
    val effectType : EffectType,
    val effectAmount : Int
)