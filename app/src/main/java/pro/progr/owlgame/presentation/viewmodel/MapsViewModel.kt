package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.repository.TownRepository
import pro.progr.owlgame.data.web.Map
import pro.progr.owlgame.presentation.ui.model.MapData
import javax.inject.Inject

class MapsViewModel @Inject constructor(
    private val townRepository: TownRepository,
    private val mapsRepository: MapsRepository
) : ViewModel() {

    var maps = mutableStateOf<List<Map>?>(emptyList())

    fun loadMaps() : Flow<List<MapData>> {
            return mapsRepository.getMaps()
    }
}