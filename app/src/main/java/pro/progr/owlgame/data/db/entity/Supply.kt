package pro.progr.owlgame.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import pro.progr.owlgame.data.db.model.EffectType

@Entity(tableName = "supplies")
data class Supply (
    @PrimaryKey
    val id : String,
    val imageUrl : String,
    val name : String,
    val description: String,
    val amount : Int = 0,
    val effectType : EffectType,
    val effectAmount : Int
)