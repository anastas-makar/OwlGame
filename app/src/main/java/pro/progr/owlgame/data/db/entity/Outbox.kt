package pro.progr.owlgame.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "outbox")
data class Outbox(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "table_name")
    val tableName: String,
    @ColumnInfo(name = "row_id")
    val rowId: String
)
