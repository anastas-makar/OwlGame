package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "supply_to_building")
data class SupplyToBuilding (
    @PrimaryKey
    val id : String,
    val supplyId : String,
    val buildingId : String,
    val amount: Int
)