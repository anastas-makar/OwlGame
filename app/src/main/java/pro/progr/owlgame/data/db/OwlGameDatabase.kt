package pro.progr.owlgame.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Town::class,
    Slot::class,
    Building::class,
    MapEntity::class,
    Animal::class,
    BuildingToAnimal::class],
    version = 1, exportSchema = false)
abstract class OwlGameDatabase : RoomDatabase() {
    abstract fun townsDao(): TownsDao
    abstract fun slotsDao(): SlotsDao

    abstract fun townWithDataDao(): MapWithDataDao

    abstract fun buildingsDao(): BuildingsDao

    abstract fun buildingWithAnimalDao(): BuildingWithAnimalDao

    abstract fun mapDao(): MapDao

    abstract fun animalDao(): AnimalDao

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
