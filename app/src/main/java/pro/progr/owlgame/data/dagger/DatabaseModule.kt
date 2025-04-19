package pro.progr.owlgame.data.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import pro.progr.owlgame.data.db.AnimalDao
import pro.progr.owlgame.data.db.BuildingToAnimalDao
import pro.progr.owlgame.data.db.BuildingsDao
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.SlotsDao
import pro.progr.owlgame.data.db.MapWithDataDao
import pro.progr.owlgame.data.db.TownsDao
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): OwlGameDatabase {
        return OwlGameDatabase.getDatabase(context)
    }

    @Provides
    fun provideTownsDao(database: OwlGameDatabase): TownsDao {
        return database.townsDao()
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
    fun provideSlotsDao(database: OwlGameDatabase): SlotsDao {
        return database.slotsDao()
    }

    @Provides
    fun provideMapDao(database: OwlGameDatabase): MapDao {
        return database.mapDao()
    }

    @Provides
    fun provideAnimalDao(database: OwlGameDatabase): AnimalDao {
        return database.animalDao()
    }

    @Provides
    fun provideBuildingToAnimalDao(database: OwlGameDatabase): BuildingToAnimalDao {
        return database.buildingToAnimalDao()
    }
}