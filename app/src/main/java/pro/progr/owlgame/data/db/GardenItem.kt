package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "garden_items")
data class GardenItem (
    @PrimaryKey
    val id : String,
    val name : String,
    val description : String,
    val imageUrl : String,
    val gardenId : String? = null,
    val x : Float = 0f,
    val y : Float = 0f,
    val supplyId : String,
    val supplyAmount : Int,
    val itemType : ItemType,
    val gardenType: GardenType,
    val readiness: Float = 0f,
    val deleted: Boolean = false
)