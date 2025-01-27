package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MapDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaps(maps: List<MapEntity>)

    @Query("SELECT * FROM maps WHERE id = :id")
    suspend fun getMapById(id: String): MapEntity?

    @Query("SELECT * FROM maps")
    suspend fun getAllMaps(): List<MapEntity>
}