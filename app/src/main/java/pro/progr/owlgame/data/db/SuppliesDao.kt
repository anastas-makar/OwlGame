package pro.progr.owlgame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SuppliesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(supplies: List<Supply>) : List<Long>

    @Query("UPDATE supplies SET amount = amount + :amount WHERE id = :supplyId")
    suspend fun updateAmount(supplyId : String, amount : Int)
}
