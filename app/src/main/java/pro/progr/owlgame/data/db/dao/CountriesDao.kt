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

    @Query(
        """
    UPDATE countries
    SET rulerAnimalId = NULL
    WHERE id = (
        SELECT countryId
        FROM maps
        WHERE id = :mapId
    )
    AND rulerAnimalId IN (
        SELECT animalId
        FROM buildings
        WHERE mapId = :mapId
          AND animalId IS NOT NULL
    )
    """
    )
    suspend fun removeRulerIfTownContainsRuler(mapId: String)

    @Query(
        """
    UPDATE maps
    SET countryId = :countryId
    WHERE id = :mapId
      AND type = 'TOWN'
    """
    )
    suspend fun moveTownInternal(
        mapId: String,
        countryId: String?
    )

    @Transaction
    suspend fun moveTown(
        mapId: String,
        countryId: String?
    ) {
        removeRulerIfTownContainsRuler(mapId)
        moveTownInternal(mapId, countryId)
    }

    @Query("UPDATE maps SET countryId = NULL WHERE countryId = :countryId")
    suspend fun releaseTowns(countryId: String)

    @Query(
        """
    UPDATE countries
    SET deleted = 1,
        rulerAnimalId = NULL
    WHERE id = :countryId
    """
    )
    suspend fun markDeleted(countryId: String)

    @Transaction
    suspend fun deleteCountry(countryId: String) {
        releaseTowns(countryId)
        markDeleted(countryId)
    }

    @Query(
        """
    UPDATE countries
    SET rulerAnimalId = :animalId
    WHERE id = :countryId
      AND deleted = 0
      AND EXISTS (
          SELECT 1
          FROM buildings
          INNER JOIN maps ON maps.id = buildings.mapId
          INNER JOIN animals ON animals.id = buildings.animalId
          WHERE buildings.animalId = :animalId
            AND buildings.type = 'FORTRESS'
            AND maps.type = 'TOWN'
            AND maps.countryId = :countryId
            AND animals.status = 'PET'
      )
    """
    )
    suspend fun appointRuler(
        countryId: String,
        animalId: String
    ): Int

    @Query(
        """
    UPDATE countries
    SET rulerAnimalId = NULL
    WHERE id = :countryId
    """
    )
    suspend fun removeRuler(countryId: String)
}