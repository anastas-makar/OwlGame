package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TownWithDataDao {
    @Query("SELECT * FROM towns WHERE id = :townId")
    fun getTownWithData(townId: Long): Flow<TownWithData>
}