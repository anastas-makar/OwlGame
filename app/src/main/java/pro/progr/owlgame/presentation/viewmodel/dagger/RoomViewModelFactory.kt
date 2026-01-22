package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.FurnitureRepository
import pro.progr.owlgame.presentation.viewmodel.RoomViewModel
import javax.inject.Inject

class RoomViewModelFactory @Inject constructor(
    private val furnitureRepository: FurnitureRepository
) : ViewModelProvider.Factory {
    var roomId: String = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            return RoomViewModel(
                furnitureRepository,
                roomId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}