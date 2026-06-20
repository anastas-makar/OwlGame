package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.model.CountryModel

interface CountriesRepository {

    fun observeCountries(): Flow<List<CountryModel>>

    suspend fun insert(country: CountryModel)

    suspend fun moveTown(mapId: String, countryId: String?)

    suspend fun deleteCountry(countryId: String)

    suspend fun appointRuler(
        countryId: String,
        animalId: String
    ): Int

    suspend fun removeRuler(countryId: String)
}