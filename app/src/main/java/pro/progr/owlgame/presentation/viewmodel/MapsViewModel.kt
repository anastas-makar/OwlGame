package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.domain.model.MapModel
import pro.progr.owlgame.domain.repository.AnimalsRepository
import javax.inject.Inject

class MapsViewModel @Inject constructor(
    private val mapsRepository: MapsRepository,
    animalsRepository: AnimalsRepository
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            animalsRepository.releaseExpiredFugitives()
        }
    }

    var maps = mutableStateOf<List<MapModel>?>(emptyList())

    fun loadMaps() : Flow<List<MapModel>> {
            return mapsRepository.getMaps()
    }
}