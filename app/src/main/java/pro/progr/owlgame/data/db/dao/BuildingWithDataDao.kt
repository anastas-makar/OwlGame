package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.embedded.BuildingWithData

@Dao
interface BuildingWithDataDao {
    @Transaction
    @Query("SELECT * FROM buildings WHERE id = :buildingId")
    fun observeBuildingWithData(buildingId: String): Flow<BuildingWithData>
}