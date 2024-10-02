package pro.progr.owlgame.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Town::class], version = 1)
abstract class OwlGameDatabase : RoomDatabase() {
    abstract fun townsDao(): TownsDao

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
