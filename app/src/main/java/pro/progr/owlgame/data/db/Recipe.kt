package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "recipes",
    foreignKeys = [
        ForeignKey(
            entity = Supply::class,
            parentColumns = ["id"],
            childColumns = ["resSupplyId"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("resSupplyId")
    ])
data class Recipe (
    @PrimaryKey
    val id : String,
    val resSupplyId : String,
    val description: String
)