package pro.progr.owlgame.data.repository.impl

import androidx.room.withTransaction
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.Recipe
import pro.progr.owlgame.data.db.RecipesDao
import pro.progr.owlgame.data.db.SuppliesDao
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.db.SupplyToRecipe
import pro.progr.owlgame.data.db.SupplyToRecipeDao
import pro.progr.owlgame.data.repository.SupplyToRecipeRepository
import javax.inject.Inject

class SupplyToRecipeRepositoryImpl @Inject constructor(
    private val db: OwlGameDatabase,
    private val suppliesDao: SuppliesDao,
    private val recipesDao: RecipesDao,
    private val supplyToRecipeDao: SupplyToRecipeDao
) : SupplyToRecipeRepository {
    override suspend fun saveRecipes(
        supplies: List<Supply>,
        recipes: List<Recipe>,
        links: List<SupplyToRecipe>
    ) {

        db.withTransaction {

            suppliesDao.insert(supplies)

            recipesDao.upsertAll(recipes)

            supplyToRecipeDao.deleteByRecipeIds(recipes.map { it.id })

            supplyToRecipeDao.upsertAll(links)
        }

    }
}