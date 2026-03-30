package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.db.AnimalStatus
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.model.SelectedSupplyAmount
import pro.progr.owlgame.data.model.StartExpeditionRequest
import pro.progr.owlgame.data.repository.AnimalsRepository
import pro.progr.owlgame.data.repository.ExpeditionsRepository
import pro.progr.owlgame.data.repository.SuppliesRepository
import pro.progr.owlgame.presentation.ui.model.ExpeditionPreparationUiState
import pro.progr.owlgame.presentation.ui.model.SupplySelectionUi
import javax.inject.Inject

class ExpeditionPreparationViewModel @Inject constructor(
    private val suppliesRepository: SuppliesRepository,
    private val animalsRepository: AnimalsRepository,
    private val expeditionsRepository: ExpeditionsRepository,
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
            suppliesRepository.getAllAvailableSupplies(),
            animalsRepository.getPets(),
            expeditionsRepository.getExpeditionWithData(mapId),
            selectedAmounts
        ) { supplies: List<Supply>,
            pets: List<Animal>,
            expeditionWithData,
            selected: Map<String, Int> ->

            val selectedAnimalId = expeditionWithData?.expedition?.animalId
            val selectedAnimal = pets.firstOrNull { it.id == selectedAnimalId }

            Pair(
                supplies.map { supply ->
                    SupplySelectionUi(
                        supply = supply,
                        selectedAmount = minOf(selected[supply.id] ?: 0, supply.amount)
                    )
                },
                Pair(pets, selectedAnimal)
            )
        }.combine(extraHealText) { base, healText ->
            Quadruple(
                base.first,
                base.second.first,
                base.second.second,
                healText
            )
        }.combine(extraDamageText) { base, damageText ->
            Quintuple(
                base.first,
                base.second,
                base.third,
                base.fourth,
                damageText
            )
        }.combine(isStarting) { base, starting ->
            Sextuple(
                base.first,
                base.second,
                base.third,
                base.fourth,
                base.fifth,
                starting
            )
        }.combine(errorMessage) { base, error ->
            ExpeditionPreparationUiState(
                items = base.first,
                availablePets = base.second,
                selectedAnimal = base.third,
                extraHealText = base.fourth,
                extraDamageText = base.fifth,
                isStarting = base.sixth,
                errorMessage = error
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ExpeditionPreparationUiState()
        )

    fun ensureAnimalSelected(expeditionId: String) {
        viewModelScope.launch {
            val expedition = withContext(Dispatchers.IO) {
                expeditionsRepository.getById(expeditionId)
            } ?: return@launch

            if (expedition.animalId != null) {
                return@launch
            }

            val pets = withContext(Dispatchers.IO) {
                animalsRepository.getPetsOnce()
            }

            if (pets.isEmpty()) {
                return@launch
            }

            val randomPet = pets.random()

            withContext(Dispatchers.IO) {
                expeditionsRepository.updateAnimalId(expeditionId, randomPet.id)
            }
        }
    }

    fun selectAnimal(expeditionId: String, animalId: String) {
        viewModelScope.launch {
            val animal = withContext(Dispatchers.IO) {
                animalsRepository.getById(animalId)
            }

            if (animal == null || animal.status != AnimalStatus.PET) {
                errorMessage.value = "Это животное нельзя отправить в экспедицию"
                return@launch
            }

            withContext(Dispatchers.IO) {
                expeditionsRepository.updateAnimalId(expeditionId, animalId)
            }
        }
    }

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

        val animalId = state.selectedAnimal?.id
        if (animalId == null) {
            errorMessage.value = "Нужно выбрать животное для экспедиции"
            return
        }

        viewModelScope.launch {
            isStarting.value = true
            errorMessage.value = null

            try {
                val result = withContext(Dispatchers.IO) {
                    expeditionsRepository.startExpedition(
                        diamondDao = diamondDao,
                        request = StartExpeditionRequest(
                            mapId = mapId,
                            expeditionId = expeditionId,
                            animalId = animalId,
                            selectedSupplies = selectedSupplies,
                            extraHeal = state.extraHeal,
                            extraDamage = state.extraDamage
                        )
                    )
                }

                if (result.isSuccess) {
                    _events.emit(Event.Started)
                } else {
                    errorMessage.value = result.exceptionOrNull()?.message
                        ?: "Не удалось начать экспедицию"
                }
            } finally {
                isStarting.value = false
            }
        }
    }

    sealed interface Event {
        data object Started : Event
    }

    private data class Quadruple<A, B, C, D>(
        val first: A,
        val second: B,
        val third: C,
        val fourth: D
    )

    private data class Quintuple<A, B, C, D, E>(
        val first: A,
        val second: B,
        val third: C,
        val fourth: D,
        val fifth: E
    )

    private data class Sextuple<A, B, C, D, E, F>(
        val first: A,
        val second: B,
        val third: C,
        val fourth: D,
        val fifth: E,
        val sixth: F
    )
}