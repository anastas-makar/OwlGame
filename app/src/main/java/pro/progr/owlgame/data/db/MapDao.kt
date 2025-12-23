package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MapDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaps(maps: List<MapEntity>)

    @Query("UPDATE maps SET name=:name AND type=:type WHERE id=:mapId")
    suspend fun setTown(mapId: String, name: String, type: MapType = MapType.TOWN)

    @Query("SELECT * FROM maps WHERE id = :id")
    fun getMapById(id: String): Flow<MapEntity?>

    @Query("SELECT * FROM maps")
    suspend fun getAllMaps(): List<MapEntity>
}