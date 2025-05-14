package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.data.web.inpouch.BuildingInPouch
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
    fun loadInPouch(pouchId : String) {
        viewModelScope.launch (Dispatchers.IO) {
            val inPouchresult = pouchesRepository.getInPouch(pouchId).getOrNull()

            if (inPouchresult != null) {
                saveInPouch(inPouchresult)
            }

        }
    }

    //Вот это в отдельный юзкейс
    //saveMapsUseCase, видимо, надо переименовать и переделать
    private fun saveInPouch(webPouch: InPouch) {
        if (webPouch.maps.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val mapsWithLocalUrls = saveMapsUseCase(webPouch.maps)
                    .map { ent ->

                        MapInPouchModel(
                            ent.id,
                            ent.name,
                            ent.imagePath
                        )
                    }

                inPouch.value = InPouch(
                    buildings = webPouch.buildings,
                    maps = mapsWithLocalUrls,
                    diamonds = webPouch.diamonds
                )
            }

        }

        if (webPouch.buildings.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val buildingsWithLocalUrls = saveBuildingsUseCase(webPouch.buildings)
                    .map { ent ->

                        BuildingInPouch(
                            ent.id,
                            ent.type,
                            ent.price,
                            ent.name,
                            ent.imageUrl
                        )
                    }

                inPouch.value = inPouch.value?.copy(buildings = buildingsWithLocalUrls)
            }
        }
    }
}