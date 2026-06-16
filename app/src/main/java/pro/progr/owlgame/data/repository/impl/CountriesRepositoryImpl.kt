package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.dao.CountriesDao
import pro.progr.owlgame.data.mapper.toData
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.domain.model.CountryModel
import pro.progr.owlgame.domain.repository.CountriesRepository
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val countriesDao: CountriesDao
) : CountriesRepository {
    override fun observeCountries(): Flow<List<CountryModel>> {
        return countriesDao.observeCountries().map {
            list -> list.map { it.toDomain() }
        }
    }

    override suspend fun insert(country: CountryModel) {
        countriesDao.insert(country.toData())
    }

    override suspend fun moveTown(mapId: String, countryId: String?) {
        countriesDao.moveTown(mapId = mapId,
            countryId = countryId)
    }

    override suspend fun deleteCountry(countryId: String) {
        countriesDao.deleteCountry(countryId)
    }
}