package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.embedded.LocationWithScenes
import pro.progr.owlgame.data.db.entity.Location

@Dao
interface LocationsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(locations: List<Location>): List<Long>

    @Transaction
    @Query("SELECT * FROM locations WHERE mapId = :mapId")
    fun getWithScenesByMapId(mapId: String): Flow<List<LocationWithScenes>>

    @Transaction
    @Query("SELECT * FROM locations WHERE mapId IS NULL")
    fun getAvailableWithScenes(): Flow<List<LocationWithScenes>>

    @Query("""
        UPDATE locations 
        SET mapId = :mapId, x = :x, y = :y 
        WHERE id = :locationId
    """)
    suspend fun placeLocationOnMap(
        locationId: String,
        mapId: String,
        x: Float,
        y: Float
    )

    @Query("""
        UPDATE locations 
        SET x = :x, y = :y 
        WHERE id = :locationId
    """)
    suspend fun updateLocationSlot(
        locationId: String,
        x: Float,
        y: Float
    )
}