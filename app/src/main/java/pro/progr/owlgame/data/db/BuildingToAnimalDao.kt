package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BuildingToAnimalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(buildingToAnimal: BuildingToAnimal): Long
}