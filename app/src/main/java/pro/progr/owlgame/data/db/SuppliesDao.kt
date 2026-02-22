package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SuppliesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(supplies: List<Supply>) : List<Long>

    @Query("UPDATE supplies SET amount = amount + :amount WHERE id = :supplyId")
    suspend fun updateAmount(supplyId : String, amount : Int)

    @Query("SELECT * FROM supplies WHERE id=:supplyId")
    fun getById(supplyId : String) : Flow<Supply?>

    @Query("SELECT * FROM supplies WHERE id IN (:ids)")
    suspend fun getByIds(ids: List<String>): List<Supply>

    @Query("SELECT * FROM supplies")
    fun observeAll(): Flow<List<Supply>>
}
