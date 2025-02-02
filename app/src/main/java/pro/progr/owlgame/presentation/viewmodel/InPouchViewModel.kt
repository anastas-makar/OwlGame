package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.data.web.inpouch.InPouch
import pro.progr.owlgame.data.web.inpouch.MapInPouchModel
import pro.progr.owlgame.domain.SaveMapsUseCase
import javax.inject.Inject

class InPouchViewModel @Inject constructor(
    private val pouchesRepository: PouchesRepository,
    private val saveMapsUseCase: SaveMapsUseCase
) : ViewModel() {
    val inPouch = mutableStateOf<InPouch?>(null)
    fun loadInPouch(pouchId : String) {
        viewModelScope.launch (Dispatchers.IO) {
            val inPouchresult = pouchesRepository.getInPouch(pouchId).getOrNull()

            if (inPouchresult != null && inPouchresult.maps.isNotEmpty()) {
                saveInPouch(inPouchresult)
            }

        }
    }

    //Вот это в отдельный юзкейс
    //saveMapsUseCase, видимо, надо переименовать и переделать
    private fun saveInPouch(webPouch: InPouch) {
        if (webPouch.maps.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                val mapsWithLocalUrls = saveMapsUseCase
                    .invoke(webPouch.maps)
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
    }
}