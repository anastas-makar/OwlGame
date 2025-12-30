package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.data.web.inpouch.InPouch
import pro.progr.owlgame.data.web.inpouch.MapInPouchModel
import pro.progr.owlgame.domain.SaveBuildingsUseCase
import pro.progr.owlgame.domain.SaveMapsUseCase
import javax.inject.Inject

class InPouchViewModel @Inject constructor(
    private val pouchesRepository: PouchesRepository,
    private val saveMapsUseCase: SaveMapsUseCase,
    private val saveBuildingsUseCase: SaveBuildingsUseCase
) : ViewModel() {

    val inPouch = mutableStateOf<InPouch?>(null)

    fun loadInPouch(pouchId: String) {
        viewModelScope.launch {
            val webPouch = withContext(Dispatchers.IO) {
                pouchesRepository.getInPouch(pouchId).getOrNull()
            } ?: return@launch

            val mapsWithLocalUrls = withContext(Dispatchers.IO) {
                if (webPouch.maps.isNotEmpty()) {
                    saveMapsUseCase(webPouch.maps).map { ent ->
                        MapInPouchModel(ent.id, ent.name, ent.imagePath)
                    }
                } else emptyList()
            }

            val buildingsWithLocalUrls = withContext(Dispatchers.IO) {
                if (webPouch.buildings.isNotEmpty()) {
                    saveBuildingsUseCase(webPouch.buildings) // уже вернёт с rooms/gardens
                } else emptyList()
            }

            // один раз выставили стейт — и всё
            inPouch.value = InPouch(
                buildings = buildingsWithLocalUrls,
                maps = mapsWithLocalUrls,
                diamonds = webPouch.diamonds
                // + остальные поля, если есть
            )
        }
    }
}
