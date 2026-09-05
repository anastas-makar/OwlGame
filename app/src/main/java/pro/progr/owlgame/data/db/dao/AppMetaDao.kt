package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppMetaDao {
    @Query("SELECT value FROM app_meta WHERE `key` = :key LIMIT 1")
    suspend fun getValue(key: String): String?

    @Query("SELECT value FROM app_meta WHERE `key` = :key LIMIT 1")
    fun observeValue(key: String): Flow<String?>

    /**
     * OkHttp interceptors are synchronous and run outside a coroutine. This query is only
     * used from an OkHttp worker thread to add the game-instance header before signing.
     */
    @Query("SELECT value FROM app_meta WHERE `key` = :key LIMIT 1")
    fun getValueBlocking(key: String): String?

    @Query("UPDATE app_meta SET value = :value WHERE `key` = :key")
    suspend fun setValue(key: String, value: String): Int
}
