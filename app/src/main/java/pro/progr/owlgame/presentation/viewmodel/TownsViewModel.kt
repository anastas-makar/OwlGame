package pro.progr.owlgame.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.repository.TownRepository
import pro.progr.owlgame.data.web.Map
import javax.inject.Inject
import javax.inject.Named

class TownsViewModel @Inject constructor(
    private val townRepository: TownRepository,
    private val mapsRepository: MapsRepository,
    @Named("baseUrl") private val baseUrl: String
) : ViewModel() {

    var townsList = townRepository.getTownsList()
    var maps = mutableStateOf<List<Map>?>(emptyList())

    fun loadMaps() {
        viewModelScope.launch {
            val result = mapsRepository.getMaps()

            result.onSuccess { mapsList ->
                maps.value = mapsList.map { m -> Map("1", "map", baseUrl + m.imageUrl) }
            }.onFailure {
                Log.e("TownsViewModel", "Failed to load maps. ERROR RESULT: $it")
            }
        }
    }
}