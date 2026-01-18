package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.PlantsRepository
import pro.progr.owlgame.presentation.viewmodel.KitchenGardenViewModel
import javax.inject.Inject

class KitchenGardenViewModelFactory @Inject constructor(
    private val plantsRepository: PlantsRepository
) : ViewModelProvider.Factory {
    var gardenId: String = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(KitchenGardenViewModel::class.java)) {
            return KitchenGardenViewModel(
                plantsRepository,
                gardenId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}