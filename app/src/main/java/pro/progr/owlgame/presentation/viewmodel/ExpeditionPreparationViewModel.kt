package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.repository.SuppliesRepository
import pro.progr.owlgame.domain.StartExpeditionUseCase
import pro.progr.owlgame.domain.model.SelectedSupplyAmount
import pro.progr.owlgame.presentation.ui.model.ExpeditionPreparationUiState
import pro.progr.owlgame.presentation.ui.model.SupplySelectionUi
import javax.inject.Inject

class ExpeditionPreparationViewModel @Inject constructor(
    private val suppliesRepository: SuppliesRepository,
    private val startExpeditionUseCase: StartExpeditionUseCase,
    private val mapId: String
) : ViewModel() {

    private val selectedAmounts = MutableStateFlow<Map<String, Int>>(emptyMap())
    private val extraHealText = MutableStateFlow("")
    private val extraDamageText = MutableStateFlow("")
    private val isStarting = MutableStateFlow(false)
    private val errorMessage = MutableStateFlow<String?>(null)

    private val _events = MutableSharedFlow<Event>()
    val events: SharedFlow<Event> = _events

    val uiState: StateFlow<ExpeditionPreparationUiState> =
        combine(
            suppliesRepository.getAllSupplies(),
            selectedAmounts,
            extraHealText,
            extraDamageText
        ) { supplies: List<Supply>,
            selected: Map<String, Int>,
            healText: String,
            damageText: String ->

            ExpeditionPreparationUiState(
                items = supplies.map { supply ->
                    SupplySelectionUi(
                        supply = supply,
                        selectedAmount = minOf(selected[supply.id] ?: 0, supply.amount)
                    )
                },
                extraHealText = healText,
                extraDamageText = damageText
            )
        }.combine(
            isStarting
        ) { state, starting ->
            state.copy(isStarting = starting)
        }.combine(
            errorMessage
        ) { state, error ->
            state.copy(errorMessage = error)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ExpeditionPreparationUiState()
        )

    fun increaseSupply(supplyId: String) {
        val item = uiState.value.items.firstOrNull { it.supply.id == supplyId } ?: return
        if (item.selectedAmount >= item.supply.amount) return

        selectedAmounts.update { old ->
            old + (supplyId to (item.selectedAmount + 1))
        }
    }

    fun decreaseSupply(supplyId: String) {
        val current = uiState.value.items.firstOrNull { it.supply.id == supplyId }?.selectedAmount ?: 0
        val next = (current - 1).coerceAtLeast(0)

        selectedAmounts.update { old ->
            if (next == 0) old - supplyId else old + (supplyId to next)
        }
    }

    fun setExtraHealText(text: String) {
        if (text.all { it.isDigit() }) {
            extraHealText.value = text
        }
    }

    fun setExtraDamageText(text: String) {
        if (text.all { it.isDigit() }) {
            extraDamageText.value = text
        }
    }

    fun clearError() {
        errorMessage.value = null
    }

    fun startExpedition(
        expeditionId: String,
        diamondDao: PurchaseInterface
    ) {
        val state = uiState.value
        val selectedSupplies = state.items
            .filter { it.selectedAmount > 0 }
            .map { SelectedSupplyAmount(it.supply.id, it.selectedAmount) }

        viewModelScope.launch {
            isStarting.value = true
            errorMessage.value = null

            val result = startExpeditionUseCase(
                diamondDao = diamondDao,
                mapId = mapId,
                expeditionId = expeditionId,
                selectedSupplies = selectedSupplies,
                extraHeal = state.extraHeal,
                extraDamage = state.extraDamage
            )

            isStarting.value = false

            if (result.isSuccess) {
                _events.emit(Event.Started)
            } else {
                errorMessage.value = result.exceptionOrNull()?.message
                    ?: "Не удалось начать экспедицию"
            }
        }
    }

    sealed interface Event {
        data object Started : Event
    }
}