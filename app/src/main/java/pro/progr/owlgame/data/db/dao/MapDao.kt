package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.entity.MapEntity
import pro.progr.owlgame.data.db.model.MapType

@Dao
interface MapDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaps(maps: List<MapEntity>)

    @Query("UPDATE maps SET name=:name, `type`=:type WHERE id=:mapId")
    suspend fun setTown(mapId: String, name: String, type: MapType = MapType.TOWN)

    @Query("SELECT * FROM maps WHERE id = :id")
    fun getMapById(id: String): Flow<MapEntity?>

    @Query("SELECT * FROM maps")
    suspend fun getAllMaps(): List<MapEntity>

    @Query("SELECT * FROM maps ORDER BY RANDOM() LIMIT 1")
    fun getRandomMap(): MapEntity?

    @Query("""
        UPDATE maps
        SET type = :type
        WHERE id = :mapId
    """)
    suspend fun updateType(
        mapId: String,
        type: MapType
    ): Int
}