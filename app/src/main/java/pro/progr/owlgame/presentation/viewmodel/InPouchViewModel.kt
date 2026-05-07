package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.domain.model.InPouchModel
import pro.progr.owlgame.domain.repository.PouchesRepository
import pro.progr.owlgame.domain.usecase.SavePouchUseCase
import javax.inject.Inject

class InPouchViewModel @Inject constructor(
    private val pouchesRepository: PouchesRepository,
    private val savePouchUseCase: SavePouchUseCase
) : ViewModel() {

    val inPouch = mutableStateOf<InPouchModel?>(null)

    private var lastLoadedPouchId: String? = null
    private var loadJob: Job? = null

    fun loadInPouch(pouchId: String, diamondDao: PurchaseInterface) {
        // если уже загружено и стейт не пустой — не повторяем
        if (pouchId == lastLoadedPouchId && inPouch.value != null) return

        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            // опционально: показать "загрузка"
            inPouch.value = null

            val webPouch = withContext(Dispatchers.IO) {
                pouchesRepository.getInPouch(pouchId).getOrNull()
            } ?: return@launch

            viewModelScope.launch {
                val newPouch = savePouchUseCase(webPouch, diamondDao)
                inPouch.value = newPouch
            }

            lastLoadedPouchId = pouchId
        }
    }
}

