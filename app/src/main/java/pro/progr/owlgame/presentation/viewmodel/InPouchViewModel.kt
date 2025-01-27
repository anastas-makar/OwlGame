package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.data.web.inpouch.InPouch
import javax.inject.Inject

import pro.progr.owlgame.data.web.inpouch.MapInPouchModel
import pro.progr.owlgame.domain.SaveMapsUseCase

class InPouchViewModel @Inject constructor(
    private val pouchesRepository: PouchesRepository,
    private val saveMapsUseCase: SaveMapsUseCase
) : ViewModel() {
    val inPouch = mutableStateOf<InPouch?>(null)
    fun loadInPouch(pouchId : String) {
        viewModelScope.launch (Dispatchers.IO) {
            inPouch.value = pouchesRepository.getInPouch(pouchId).getOrNull()

            inPouch.value?.let {
                saveMaps(it.maps)
            }

        }


    }

    private fun saveMaps(maps : List<MapInPouchModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            saveMapsUseCase.invoke(maps)
        }
    }
}