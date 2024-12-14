package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TownWithDataDao {
    @Query("SELECT * FROM towns WHERE id = :townId")
    fun getTownWithData(townId: Long): TownWithData
}