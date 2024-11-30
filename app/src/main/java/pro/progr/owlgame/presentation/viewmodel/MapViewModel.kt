package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.web.Map
import pro.progr.owlgame.domain.FoundTownUseCase
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val mapId: String,
    private val foundTownUseCase: FoundTownUseCase
) : ViewModel() {

    var map : Flow<Map> = mapsRepository.getMapById(mapId)

    val foundTown = MutableStateFlow(false)

    fun foundTown(map: Map, townName: String ) {
        viewModelScope.launch (Dispatchers.Default) {
            foundTown.update { _ -> true }
            foundTownUseCase.invoke(map, townName)
        }
    }

}