package pro.progr.owlgame.data.dagger

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import pro.progr.owlgame.data.db.OwlGameDatabase
import pro.progr.owlgame.data.db.SlotsDao
import pro.progr.owlgame.data.db.TownWithDataDao
import pro.progr.owlgame.data.db.TownsDao
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): OwlGameDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            OwlGameDatabase::class.java,
            "owl_game_database"
        ).build()
    }

    @Provides
    fun provideTownsDao(database: OwlGameDatabase): TownsDao {
        return database.townsDao()
    }

    @Provides
    fun provideTownWithDataDao(database: OwlGameDatabase): TownWithDataDao {
        return database.townWithDataDao()
    }

    @Provides
    fun provideSlotsDao(database: OwlGameDatabase): SlotsDao {
        return database.slotsDao()
    }
}