package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AppMetaDao {
    @Query("SELECT value FROM app_meta WHERE `key` = :key LIMIT 1")
    suspend fun getValue(key: String): String?

    @Query("UPDATE app_meta SET value = :value WHERE `key` = :key")
    suspend fun setValue(key: String, value: String): Int
}
