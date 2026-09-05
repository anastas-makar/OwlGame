package pro.progr.owlgame.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.progr.owlgame.domain.repository.PouchesRepository
import pro.progr.owlgame.domain.model.PouchOfferModel
import javax.inject.Inject

class PouchesViewModel @Inject constructor(
    private val pouchesRepository: PouchesRepository
) : ViewModel() {

    val pouchOffer = mutableStateOf<PouchOfferModel?>(null)

    val isPouchSelected = mutableStateOf(false)
    val selectedImageUrl = mutableStateOf<String?>(null)

    fun loadPouches() {
        viewModelScope.launch {
            val result = pouchesRepository.getPouchOffer()

            result.onSuccess { offer ->
                pouchOffer.value = offer
            }.onFailure {
                Log.e("PouchesViewModel", "Failed to load pouches. ERROR RESULT: $it")
            }
        }
    }
}
