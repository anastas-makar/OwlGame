package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class Recipe (
    @PrimaryKey
    val id : String,
    val resSupplyId : String,
    val description: String,
    val effectType : EffectType,
    val effectAmount : Int
)