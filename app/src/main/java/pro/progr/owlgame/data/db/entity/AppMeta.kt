package pro.progr.owlgame.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_meta")
data class AppMeta(
    @PrimaryKey
    val key: String,
    val value: String
)
