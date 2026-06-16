package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.CountriesRepository
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.presentation.viewmodel.MapsViewModel
import javax.inject.Inject

class MapsViewModelFactory @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val animalsRepository: AnimalsRepository,
    private val countriesRepository: CountriesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(mapsRepository,
                animalsRepository,
                countriesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}