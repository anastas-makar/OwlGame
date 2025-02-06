package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.repository.TownRepository
import pro.progr.owlgame.presentation.viewmodel.MapsViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val townRepository: TownRepository,
    private val mapsRepository: MapsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            return MapsViewModel(townRepository, mapsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}