package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.temporal.TemporalAmount

@Entity(tableName = "supplies")
data class SupplyToBuilding (
    @PrimaryKey
    val id : String,
    val supplyId : String,
    val buildingId : String,
    val amount: Int
)