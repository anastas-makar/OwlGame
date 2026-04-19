package pro.progr.owlgame

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.dao.RecipesDao
import pro.progr.owlgame.data.db.dao.SuppliesDao
import pro.progr.owlgame.data.db.dao.SupplyToRecipeDao
import pro.progr.owlgame.data.repository.impl.SupplyToRecipeRepositoryImpl
import pro.progr.owlgame.domain.model.CraftResult
import pro.progr.owlgame.domain.model.EffectType
import pro.progr.owlgame.domain.model.IngredientWithSupplyModel
import pro.progr.owlgame.domain.model.RecipeWithSuppliesModel
import pro.progr.owlgame.domain.model.SupplyModel

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class SupplyToRecipeRepositoryImplTest {

    private lateinit var db: OwlGameDatabase
    private lateinit var suppliesDao: SuppliesDao
    private lateinit var recipesDao: RecipesDao
    private lateinit var supplyToRecipeDao: SupplyToRecipeDao
    private lateinit var repository: SupplyToRecipeRepositoryImpl

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, OwlGameDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        // Если у тебя accessors в базе называются иначе — поправь эти 3 строки.
        suppliesDao = db.suppliesDao()
        recipesDao = db.recipesDao()
        supplyToRecipeDao = db.supplyToRecipesDao()

        repository = SupplyToRecipeRepositoryImpl(
            db = db,
            suppliesDao = suppliesDao,
            recipesDao = recipesDao,
            supplyToRecipeDao = supplyToRecipeDao
        )
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun saveRecipes_emptyList_doesNothing() = runTest {
        repository.saveRecipes(emptyList())

        assertTrue(suppliesDao.observeAll().first().isEmpty())
        assertTrue(recipesDao.observeAll().first().isEmpty())
        assertTrue(supplyToRecipeDao.observeAll().first().isEmpty())
    }

    @Test
    fun saveRecipes_insertsUniqueSupplies_andSumsDuplicateIngredients() = runTest {
        val wood = supplyModel(id = "wood", name = "Wood", amount = 10)
        val herb = supplyModel(id = "herb", name = "Herb", amount = 3)
        val potion = supplyModel(id = "potion", name = "Potion", amount = 0)

        val recipe = recipeWithSupplies(
            recipeId = "recipe_1",
            result = potion,
            description = "Potion recipe",
            ingredients = listOf(
                ingredient(wood, 2),
                ingredient(wood, 5),
                ingredient(herb, 1)
            )
        )

        repository.saveRecipes(listOf(recipe))

        val allSupplies = suppliesDao.observeAll().first()
        assertEquals(3, allSupplies.size)

        val allRecipes = recipesDao.observeAll().first()
        assertEquals(1, allRecipes.size)
        assertEquals("potion", allRecipes.single().resSupplyId)

        val links = supplyToRecipeDao.getByRecipe("recipe_1")
        assertEquals(2, links.size)

        val woodLink = links.first { it.supplyId == "wood" }
        val herbLink = links.first { it.supplyId == "herb" }

        assertEquals(7, woodLink.amount)
        assertEquals(1, herbLink.amount)
    }

    @Test
    fun saveRecipes_resavesRecipe_replacesActiveLinks() = runTest {
        val wood = supplyModel(id = "wood", name = "Wood", amount = 10)
        val herb = supplyModel(id = "herb", name = "Herb", amount = 10)
        val potion = supplyModel(id = "potion", name = "Potion", amount = 0)

        val firstVersion = recipeWithSupplies(
            recipeId = "recipe_1",
            result = potion,
            description = "Potion v1",
            ingredients = listOf(
                ingredient(wood, 2),
                ingredient(herb, 1)
            )
        )

        val secondVersion = recipeWithSupplies(
            recipeId = "recipe_1",
            result = potion,
            description = "Potion v2",
            ingredients = listOf(
                ingredient(wood, 4)
            )
        )

        repository.saveRecipes(listOf(firstVersion))
        repository.saveRecipes(listOf(secondVersion))

        val links = supplyToRecipeDao.getByRecipe("recipe_1")
        assertEquals(1, links.size)
        assertEquals("wood", links.single().supplyId)
        assertEquals(4, links.single().amount)

        val observed = repository.observeRecipes().first()
        assertEquals(1, observed.size)
        assertEquals("Potion v2", observed.single().description)
        assertEquals(1, observed.single().ingredients.size)
        assertEquals("wood", observed.single().ingredients.single().supplyId)
    }

    @Test
    fun craftSupplyByRecipe_returnsNotFound_whenRecipeDoesNotExist() = runTest {
        val result = repository.craftSupplyByRecipe("missing_recipe")

        assertEquals(CraftResult.NotFound, result)
    }

    @Test
    fun craftSupplyByRecipe_returnsNotEnoughIngredients_whenInventoryIsTooSmall() = runTest {
        val wood = supplyModel(id = "wood", name = "Wood", amount = 1)
        val potion = supplyModel(id = "potion", name = "Potion", amount = 0)

        repository.saveRecipes(
            listOf(
                recipeWithSupplies(
                    recipeId = "recipe_1",
                    result = potion,
                    description = "Potion recipe",
                    ingredients = listOf(
                        ingredient(wood, 2)
                    )
                )
            )
        )

        val result = repository.craftSupplyByRecipe("recipe_1")

        assertEquals(CraftResult.NotEnoughIngredients, result)

        val supplies = suppliesDao.getByIds(listOf("wood", "potion")).associateBy { it.id }
        assertEquals(1, supplies.getValue("wood").amount)
        assertEquals(0, supplies.getValue("potion").amount)
    }

    @Test
    fun craftSupplyByRecipe_consumesIngredients_andAddsResult_whenEnoughIngredients() = runTest {
        val wood = supplyModel(id = "wood", name = "Wood", amount = 5)
        val herb = supplyModel(id = "herb", name = "Herb", amount = 1)
        val potion = supplyModel(id = "potion", name = "Potion", amount = 0)

        repository.saveRecipes(
            listOf(
                recipeWithSupplies(
                    recipeId = "recipe_1",
                    result = potion,
                    description = "Potion recipe",
                    ingredients = listOf(
                        ingredient(wood, 2),
                        ingredient(herb, 1)
                    )
                )
            )
        )

        val result = repository.craftSupplyByRecipe("recipe_1")

        assertEquals(CraftResult.Success, result)

        val supplies = suppliesDao.getByIds(listOf("wood", "herb", "potion")).associateBy { it.id }
        assertEquals(3, supplies.getValue("wood").amount)
        assertEquals(0, supplies.getValue("herb").amount)
        assertEquals(1, supplies.getValue("potion").amount)
    }

    @Test
    fun observeRecipes_mapsDataCorrectly_andUpdatesCraftableFlag() = runTest {
        val wood = supplyModel(id = "wood", name = "Wood", amount = 1)
        val herb = supplyModel(id = "herb", name = "Herb", amount = 1)
        val potion = supplyModel(id = "potion", name = "Potion", amount = 0)

        repository.saveRecipes(
            listOf(
                recipeWithSupplies(
                    recipeId = "recipe_1",
                    result = potion,
                    description = "Potion recipe",
                    ingredients = listOf(
                        ingredient(wood, 2),
                        ingredient(herb, 1)
                    )
                )
            )
        )

        val firstSnapshot = repository.observeRecipes().first()
        assertEquals(1, firstSnapshot.size)

        val recipe = firstSnapshot.single()
        assertEquals("recipe_1", recipe.recipeId)
        assertEquals("potion", recipe.resultSupplyId)
        assertEquals("Potion", recipe.resultName)
        assertEquals("potion.png", recipe.resultImageUrl)
        assertEquals("Potion recipe", recipe.description)
        assertFalse(recipe.craftable)
        assertEquals(2, recipe.ingredients.size)

        val woodIngredient = recipe.ingredients.first { it.supplyId == "wood" }
        assertEquals("Wood", woodIngredient.name)
        assertEquals("wood.png", woodIngredient.imageUrl)
        assertEquals(2, woodIngredient.required)
        assertEquals(1, woodIngredient.have)

        val herbIngredient = recipe.ingredients.first { it.supplyId == "herb" }
        assertEquals(1, herbIngredient.required)
        assertEquals(1, herbIngredient.have)

        suppliesDao.updateAmount("wood", 1)

        val secondSnapshot = repository.observeRecipes().first()
        assertTrue(secondSnapshot.single().craftable)
    }

    private fun recipeWithSupplies(
        recipeId: String,
        result: SupplyModel,
        description: String,
        ingredients: List<IngredientWithSupplyModel>
    ): RecipeWithSuppliesModel {
        return RecipeWithSuppliesModel(
            recipeId = recipeId,
            resultSupply = result,
            resultName = result.name,
            resultImageUrl = result.imageUrl,
            description = description,
            ingredients = ingredients,
            craftable = false
        )
    }

    private fun ingredient(
        supply: SupplyModel,
        amount: Int
    ): IngredientWithSupplyModel {
        return IngredientWithSupplyModel(
            supplyModel = supply,
            amount = amount
        )
    }

    private fun supplyModel(
        id: String,
        name: String = id,
        amount: Int = 0,
        imageUrl: String = "$id.png"
    ): SupplyModel {
        return SupplyModel(
            id = id,
            imageUrl = imageUrl,
            name = name,
            description = "$name description",
            amount = amount,
            effectType = EffectType.NO_EFFECT,
            effectAmount = 0
        )
    }
}