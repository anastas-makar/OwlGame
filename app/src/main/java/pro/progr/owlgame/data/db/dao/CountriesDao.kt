package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.entity.Country

@Dao
interface CountriesDao {

    @Query("SELECT * FROM countries WHERE deleted = 0 ORDER BY name")
    fun observeCountries(): Flow<List<Country>>

    @Insert
    suspend fun insert(country: Country)

    @Query("UPDATE maps SET countryId = :countryId WHERE id = :mapId AND type = 'TOWN'")
    suspend fun moveTown(mapId: String, countryId: String?)

    @Query("UPDATE maps SET countryId = NULL WHERE countryId = :countryId")
    suspend fun releaseTowns(countryId: String)

    @Query("UPDATE countries SET deleted = 1 WHERE id = :countryId")
    suspend fun markDeleted(countryId: String)

    @Transaction
    suspend fun deleteCountry(countryId: String) {
        releaseTowns(countryId)
        markDeleted(countryId)
    }
}