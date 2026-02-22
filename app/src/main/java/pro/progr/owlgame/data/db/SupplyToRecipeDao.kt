package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplyToRecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<SupplyToRecipe>)

    @Query("UPDATE supply_to_recipe SET deleted = 1 WHERE recipeId IN (:recipeIds)")
    suspend fun markDeletedByRecipeIds(recipeIds: List<String>)

    @Query("SELECT * FROM supply_to_recipe WHERE deleted = 0")
    fun observeAll(): Flow<List<SupplyToRecipe>>

    @Query("SELECT * FROM supply_to_recipe WHERE recipeId = :recipeId AND deleted = 0")
    suspend fun getByRecipe(recipeId: String): List<SupplyToRecipe>
}