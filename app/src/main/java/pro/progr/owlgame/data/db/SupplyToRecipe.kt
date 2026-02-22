package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "supply_to_recipe",
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Supply::class,
            parentColumns = ["id"],
            childColumns = ["supplyId"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["recipeId", "supplyId"], unique = true),
        Index("recipeId"),
        Index("supplyId")
    ])
data class SupplyToRecipe (
    @PrimaryKey
    val id : String,
    val supplyId : String,
    val recipeId : String,
    val amount: Int,
    val deleted: Boolean = false
)