package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pro.progr.diamondapi.PurchaseInterface
import pro.progr.owlgame.data.db.Furniture
import pro.progr.owlgame.data.repository.FurnitureRepository

class RoomViewModel @Inject constructor(
    private val furnitureRepository: FurnitureRepository,
    private val roomId: String
) : ViewModel() {

    val selectFurnitureItemState: MutableState<Boolean> = mutableStateOf(false)

    val furnitureItems: StateFlow<List<Furniture>> =
        furnitureRepository.observeByRoomId(roomId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availableFurnitureItems: StateFlow<List<Furniture>> = furnitureRepository.getAvailableFurnitureItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updatePos(id: String, x: Float, y: Float) {
        viewModelScope.launch(Dispatchers.IO) { furnitureRepository.updatePos(id, x, y) }
    }

    fun setFurnitureItem(furniture: Furniture, pI: PurchaseInterface) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = pI.spendDiamonds(furniture.price)
            if(res.isSuccess) {
                furnitureRepository.setFurniture(furniture.id, roomId)
            }
        }
        selectFurnitureItemState.value = false
    }
}