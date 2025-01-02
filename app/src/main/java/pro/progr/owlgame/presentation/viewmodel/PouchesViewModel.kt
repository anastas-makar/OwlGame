package pro.progr.owlgame.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.data.web.Pouch
import javax.inject.Inject

class PouchesViewModel @Inject constructor(
    private val pouchesRepository: PouchesRepository
) : ViewModel() {

    var pouches = mutableStateOf<List<Pouch>?>(emptyList())

    fun loadPouches() {
        viewModelScope.launch {
            val result = pouchesRepository.getPouches()

            result.onSuccess { pouchesList ->
                pouches.value = pouchesList
            }.onFailure {
                Log.e("PouchesViewModel", "Failed to load pouches. ERROR RESULT: $it")
            }
        }
    }
}