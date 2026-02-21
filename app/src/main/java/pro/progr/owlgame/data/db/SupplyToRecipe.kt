package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "supply_to_recipe",
    indices = [Index(value = ["recipeId", "supplyId"], unique = true)])
data class SupplyToRecipe (
    @PrimaryKey
    val id : String,
    val supplyId : String,
    val recipeId : String,
    val amount: Int
)