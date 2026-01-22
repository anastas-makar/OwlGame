package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.data.web.inpouch.InPouch
import pro.progr.owlgame.data.web.inpouch.MapInPouchModel
import pro.progr.owlgame.domain.SaveBuildingsUseCase
import pro.progr.owlgame.domain.SaveFurnitureUseCase
import pro.progr.owlgame.domain.SaveGardenItemsUseCase
import pro.progr.owlgame.domain.SaveMapsUseCase
import pro.progr.owlgame.domain.SavePlantsUseCase
import javax.inject.Inject

class InPouchViewModel @Inject constructor(
    private val pouchesRepository: PouchesRepository,
    private val saveMapsUseCase: SaveMapsUseCase,
    private val saveBuildingsUseCase: SaveBuildingsUseCase,
    private val savePlantsUseCase: SavePlantsUseCase,
    private val saveGardenItemsUseCase: SaveGardenItemsUseCase,
    private val saveFurnitureUseCase: SaveFurnitureUseCase
) : ViewModel() {

    val inPouch = mutableStateOf<InPouch?>(null)

    private var lastLoadedPouchId: String? = null
    private var loadJob: Job? = null

    fun loadInPouch(pouchId: String) {
        // если уже загружено и стейт не пустой — не повторяем
        if (pouchId == lastLoadedPouchId && inPouch.value != null) return

        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            // опционально: показать "загрузка"
            inPouch.value = null

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
                    saveBuildingsUseCase(webPouch.buildings)
                } else emptyList()
            }

            val plantsWithLocalUrls = withContext(Dispatchers.IO) {
                if (webPouch.plants.isNotEmpty()) {
                    savePlantsUseCase(webPouch.plants)
                } else emptyList()
            }

            val gardenItemsWithLocalUrls = withContext(Dispatchers.IO) {
                if (webPouch.gardenItems.isNotEmpty()) {
                    saveGardenItemsUseCase(webPouch.gardenItems)
                } else emptyList()
            }

            val furnitureWithLocalUrls = withContext(Dispatchers.IO) {
                if (webPouch.furniture.isNotEmpty()) {
                    saveFurnitureUseCase(webPouch.furniture)
                } else emptyList()
            }

            inPouch.value = InPouch(
                buildings = buildingsWithLocalUrls,
                maps = mapsWithLocalUrls,
                plants = plantsWithLocalUrls,
                gardenItems = gardenItemsWithLocalUrls,
                diamonds = webPouch.diamonds,
                furniture = furnitureWithLocalUrls
            )
            lastLoadedPouchId = pouchId
        }
    }
}

