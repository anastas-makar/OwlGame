package pro.progr.owlgame.data.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
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
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.dao.ExpeditionMedalDao
import pro.progr.owlgame.data.db.dao.MapWithDataDao
import pro.progr.owlgame.data.db.dao.PlantsDao
import pro.progr.owlgame.data.db.dao.RecipesDao
import pro.progr.owlgame.data.db.dao.RoomsDao
import pro.progr.owlgame.data.db.dao.StreetsDao
import pro.progr.owlgame.data.db.dao.SuppliesDao
import pro.progr.owlgame.data.db.dao.SupplyToRecipeDao
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): OwlGameDatabase {
        return OwlGameDatabase.getDatabase(context)
    }

    @Provides
    fun provideTownWithDataDao(database: OwlGameDatabase): MapWithDataDao {
        return database.townWithDataDao()
    }

    @Provides
    fun provideBuildingsDao(database: OwlGameDatabase): BuildingsDao {
        return database.buildingsDao()
    }

    @Provides
    fun provideBuildingWithAnimalDao(database: OwlGameDatabase): BuildingWithAnimalDao {
        return database.buildingWithAnimalDao()
    }

    @Provides
    fun provideBuildingWithDataDao(database: OwlGameDatabase): BuildingWithDataDao {
        return database.buildingWithDataDao()
    }

    @Provides
    fun provideExpeditionWithDataDao(database: OwlGameDatabase): ExpeditionWithDataDao {
        return database.expeditionWithDataDao()
    }

    @Provides
    fun provideMapDao(database: OwlGameDatabase): MapDao {
        return database.mapDao()
    }

    @Provides
    fun provideStreetsDao(database: OwlGameDatabase): StreetsDao {
        return database.streetsDao()
    }

    @Provides
    fun provideAnimalDao(database: OwlGameDatabase): AnimalDao {
        return database.animalDao()
    }

    @Provides
    fun provideGardensDao(database: OwlGameDatabase): GardensDao {
        return database.gardensDao()
    }

    @Provides
    fun provideRoomsDao(database: OwlGameDatabase): RoomsDao {
        return database.roomsDao()
    }

    @Provides
    fun provideGardenItemsDao(database: OwlGameDatabase): GardenItemsDao {
        return database.gardenItemsDao()
    }

    @Provides
    fun providePlantsDao(database: OwlGameDatabase): PlantsDao {
        return database.plantsDao()
    }

    @Provides
    fun provideFurnitureDao(database: OwlGameDatabase): FurnitureDao {
        return database.furnitureDao()
    }

    @Provides
    fun provideExpeditionDao(database: OwlGameDatabase): ExpeditionDao {
        return database.expeditionDao()
    }

    @Provides
    fun provideEnemyDao(database: OwlGameDatabase): EnemyDao {
        return database.enemyDao()
    }

    @Provides
    fun provideSuppliesDao(database: OwlGameDatabase): SuppliesDao {
        return database.suppliesDao()
    }

    @Provides
    fun provideRecipesDao(database: OwlGameDatabase): RecipesDao {
        return database.recipesDao()
    }

    @Provides
    fun provideSupplyToRecipesDao(database: OwlGameDatabase): SupplyToRecipeDao {
        return database.supplyToRecipesDao()
    }

    @Provides
    fun provideExpeditionMedalDao(database: OwlGameDatabase): ExpeditionMedalDao {
        return database.expeditionMedalDao()
    }
}