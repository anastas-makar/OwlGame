package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
public interface BuildingsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(buildings: List<Building>): List<Long>

    @Query("UPDATE buildings SET mapId=:mapId WHERE id=:buildingId")
    fun updateMapId(buildingId: Int, mapId: String): Int

    @Query("SELECT * FROM buildings WHERE mapId IS NULL")
    fun getAvailable() : Flow<List<Building>>

    @Query("SELECT * FROM buildings WHERE mapId=:mapId")
    fun getOnMap(mapId: String) : Flow<List<Building>>
}
