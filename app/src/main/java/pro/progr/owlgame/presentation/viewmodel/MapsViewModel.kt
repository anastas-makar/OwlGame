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
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.CountryModel
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.domain.model.MapModel
import pro.progr.owlgame.domain.model.MapType
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.CountriesRepository
import pro.progr.owlgame.presentation.ui.model.CountrySection
import pro.progr.owlgame.presentation.ui.model.MapsScreenState
import java.util.UUID
import javax.inject.Inject

class MapsViewModel @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val animalsRepository: AnimalsRepository,
    private val countriesRepository: CountriesRepository
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

    var showCreateCountryDialog = mutableStateOf(false)
        private set

    fun openCreateCountryDialog() {
        showCreateCountryDialog.value = true
    }

    fun closeCreateCountryDialog() {
        showCreateCountryDialog.value = false
    }

    fun createCountry(name: String) {
        val trimmedName = name.trim()
        if (trimmedName.isBlank()) return

        showCreateCountryDialog.value = false

        viewModelScope.launch(Dispatchers.IO) {
            countriesRepository.insert(
                CountryModel(
                    id = UUID.randomUUID().toString(),
                    name = trimmedName,
                    rulerAnimalId = null
                )
            )
        }
    }

    fun moveTownToCountry(
        mapId: String,
        countryId: String?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            countriesRepository.moveTown(
                mapId = mapId,
                countryId = countryId
            )
        }
    }

    fun deleteCountry(countryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            countriesRepository.deleteCountry(countryId)
        }
    }

    fun observeRulerCandidates(countryId: String): Flow<List<AnimalModel>> {
        return animalsRepository.observeRulerCandidates(countryId)
    }

    fun observeAnimal(animalId: String): Flow<AnimalModel?> {
        return animalsRepository.getAnimalById(animalId)
    }

    fun appointRuler(countryId: String, animalId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            countriesRepository.appointRuler(
                countryId = countryId,
                animalId = animalId
            )
        }
    }

    fun removeRuler(countryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            countriesRepository.removeRuler(countryId)
        }
    }
}