package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.repository.TownRepository
import pro.progr.owlgame.presentation.viewmodel.TownsViewModel
import javax.inject.Inject
import javax.inject.Named

class ViewModelFactory @Inject constructor(
    private val townRepository: TownRepository,
    private val mapsRepository: MapsRepository,
    @Named("baseUrl") private val baseUrl: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TownsViewModel::class.java)) {
            return TownsViewModel(townRepository, mapsRepository, baseUrl) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}