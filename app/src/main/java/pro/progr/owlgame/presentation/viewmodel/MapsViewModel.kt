package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.domain.model.MapModel
import pro.progr.owlgame.domain.model.MapType
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.CountriesRepository
import pro.progr.owlgame.presentation.ui.model.CountrySection
import pro.progr.owlgame.presentation.ui.model.MapsScreenState
import javax.inject.Inject

class MapsViewModel @Inject constructor(
    private val mapsRepository: MapsRepository,
    animalsRepository: AnimalsRepository,
    countriesRepository: CountriesRepository
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            animalsRepository.releaseExpiredFugitives()
        }
    }

    val screenState = combine(
        mapsRepository.getMaps(),
        countriesRepository.observeCountries()
    ) { maps, countries ->

        val towns = maps.filter { it.type == MapType.TOWN }
        val countryIds = countries.mapTo(mutableSetOf()) { it.id }

        MapsScreenState(
            freeTowns = towns.filter {
                it.countryId == null || it.countryId !in countryIds
            },
            countries = countries.map { country ->
                CountrySection(
                    country = country,
                    towns = towns.filter { it.countryId == country.id }
                )
            },
            freeMaps = maps.filter { it.type == MapType.FREE },
            occupiedMaps = maps.filter {
                it.type == MapType.OCCUPIED ||
                        it.type == MapType.EXPEDITION
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MapsScreenState()
    )

    var maps = mutableStateOf<List<MapModel>?>(emptyList())

    fun loadMaps() : Flow<List<MapModel>> {
            return mapsRepository.getMaps()
    }
}