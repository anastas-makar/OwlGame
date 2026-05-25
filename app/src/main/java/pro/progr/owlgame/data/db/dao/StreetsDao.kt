package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.entity.Street

@Dao
interface StreetsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(street: Street): Long

    @Query("UPDATE streets SET deleted = 1 WHERE id = :streetId")
    suspend fun deleteById(streetId: String)

    @Query("SELECT * FROM streets WHERE mapId = :mapId AND deleted = 0")
    fun getByMapId(mapId: String) : Flow<List<Street>>
}
