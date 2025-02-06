package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MapWithDataDao {
    @Transaction
    @Query("SELECT * FROM maps WHERE id = :mapId")
    fun getMapWithData(mapId: String): Flow<MapWithData>
    @Transaction
    @Query("SELECT * FROM maps")
    fun getMapsWithData(): Flow<List<MapWithData>>
}