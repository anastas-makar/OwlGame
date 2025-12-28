package pro.progr.owlgame.data.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import pro.progr.owlgame.data.db.AnimalDao
import pro.progr.owlgame.data.db.BuildingWithAnimalDao
import pro.progr.owlgame.data.db.BuildingsDao
import pro.progr.owlgame.data.db.GardensDao
import pro.progr.owlgame.data.db.MapDao
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.MapWithDataDao
import pro.progr.owlgame.data.db.RoomsDao
import pro.progr.owlgame.data.db.StreetsDao
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
}