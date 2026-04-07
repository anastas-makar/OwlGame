package pro.progr.owlgame.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pro.progr.owlgame.data.db.dao.AnimalDao
import pro.progr.owlgame.data.db.dao.BuildingWithAnimalDao
import pro.progr.owlgame.data.db.dao.BuildingWithDataDao
import pro.progr.owlgame.data.db.dao.BuildingsDao
import pro.progr.owlgame.data.db.dao.EnemyDao
import pro.progr.owlgame.data.db.dao.ExpeditionDao
import pro.progr.owlgame.data.db.dao.ExpeditionWithDataDao
import pro.progr.owlgame.data.db.dao.FurnitureDao
import pro.progr.owlgame.data.db.dao.GardenItemsDao
import pro.progr.owlgame.data.db.dao.GardensDao
import pro.progr.owlgame.data.db.dao.MapDao
import pro.progr.owlgame.data.db.dao.MapWithDataDao
import pro.progr.owlgame.data.db.dao.PlantsDao
import pro.progr.owlgame.data.db.dao.RecipesDao
import pro.progr.owlgame.data.db.dao.RoomsDao
import pro.progr.owlgame.data.db.dao.StreetsDao
import pro.progr.owlgame.data.db.dao.SuppliesDao
import pro.progr.owlgame.data.db.dao.SupplyToRecipeDao
import pro.progr.owlgame.data.db.entity.Animal
import pro.progr.owlgame.data.db.entity.Book
import pro.progr.owlgame.data.db.entity.Building
import pro.progr.owlgame.data.db.entity.Chapter
import pro.progr.owlgame.data.db.entity.Enemy
import pro.progr.owlgame.data.db.entity.Expedition
import pro.progr.owlgame.data.db.entity.Furniture
import pro.progr.owlgame.data.db.entity.Garden
import pro.progr.owlgame.data.db.entity.GardenItem
import pro.progr.owlgame.data.db.entity.MapEntity
import pro.progr.owlgame.data.db.entity.Plant
import pro.progr.owlgame.data.db.entity.Recipe
import pro.progr.owlgame.data.db.entity.RoomEntity
import pro.progr.owlgame.data.db.entity.Street
import pro.progr.owlgame.data.db.entity.Supply
import pro.progr.owlgame.data.db.entity.SupplyToRecipe

@Database(entities = [
    Building::class,
    MapEntity::class,
    Animal::class,
    Street::class,
    Garden::class,
    GardenItem::class,
    Plant::class,
    Recipe::class,
    Furniture::class,
    Expedition::class,
    Enemy::class,
    RoomEntity::class,
    Supply::class,
    SupplyToRecipe::class,
    Book::class,
    Chapter::class],
    version = 1, exportSchema = false)
abstract class OwlGameDatabase : RoomDatabase() {

    abstract fun townWithDataDao(): MapWithDataDao

    abstract fun buildingsDao(): BuildingsDao

    abstract fun buildingWithAnimalDao(): BuildingWithAnimalDao

    abstract fun buildingWithDataDao(): BuildingWithDataDao

    abstract fun expeditionWithDataDao(): ExpeditionWithDataDao

    abstract fun mapDao(): MapDao

    abstract fun streetsDao(): StreetsDao

    abstract fun animalDao(): AnimalDao

    abstract fun gardensDao(): GardensDao

    abstract fun roomsDao(): RoomsDao

    abstract fun gardenItemsDao(): GardenItemsDao

    abstract fun plantsDao(): PlantsDao

    abstract fun furnitureDao(): FurnitureDao
    
    abstract fun expeditionDao(): ExpeditionDao
    
    abstract fun enemyDao(): EnemyDao

    abstract fun suppliesDao(): SuppliesDao

    abstract fun recipesDao(): RecipesDao

    abstract fun supplyToRecipesDao(): SupplyToRecipeDao

    companion object {
        @Volatile
        private var INSTANCE: OwlGameDatabase? = null

        fun getDatabase(context: Context): OwlGameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OwlGameDatabase::class.java,
                    "owl_game_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
