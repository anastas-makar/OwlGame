package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.data.web.Map
import pro.progr.owlgame.domain.model.MapModel
import pro.progr.owlgame.domain.model.MapWithDataModel
import javax.inject.Inject

class MapsViewModel @Inject constructor(
    private val mapsRepository: MapsRepository
) : ViewModel() {

    var maps = mutableStateOf<List<Map>?>(emptyList())

    fun loadMaps() : Flow<List<MapModel>> {
            return mapsRepository.getMaps()
    }
}