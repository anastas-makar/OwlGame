package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface BuildingWithAnimalDao {
    @Transaction
    @Query("SELECT * FROM buildings WHERE mapId = :mapId")
    fun getBuildingsWithAnimals(mapId : String) : Flow<List<BuildingWithAnimal>>
    @Transaction
    @Query("SELECT * FROM buildings")
    fun getBuildingsWithAnimals() : Flow<List<BuildingWithAnimal>>
}