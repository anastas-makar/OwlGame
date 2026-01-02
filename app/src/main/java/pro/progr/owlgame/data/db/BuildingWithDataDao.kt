package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface BuildingWithDataDao {
    @Transaction
    @Query("SELECT * FROM buildings WHERE id = :buildingId")
    fun observeBuildingWithData(buildingId: String): Flow<BuildingWithData>
}