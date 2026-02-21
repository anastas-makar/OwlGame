package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SupplyToRecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<SupplyToRecipe>)

    @Query("DELETE FROM supply_to_recipe WHERE recipeId IN (:recipeIds)")
    suspend fun deleteByRecipeIds(recipeIds: List<String>)
}