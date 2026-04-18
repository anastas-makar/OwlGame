package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.domain.model.MapModel
import javax.inject.Inject

class MapsViewModel @Inject constructor(
    private val mapsRepository: MapsRepository
) : ViewModel() {

    var maps = mutableStateOf<List<MapModel>?>(emptyList())

    fun loadMaps() : Flow<List<MapModel>> {
            return mapsRepository.getMaps()
    }
}