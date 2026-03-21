package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.SuppliesRepository
import pro.progr.owlgame.domain.StartExpeditionUseCase
import pro.progr.owlgame.presentation.viewmodel.ExpeditionPreparationViewModel
import javax.inject.Inject

class ExpeditionPreparationViewModelFactory @Inject constructor(
    private val suppliesRepository: SuppliesRepository,
    private val startExpeditionUseCase: StartExpeditionUseCase
) : ViewModelProvider.Factory {
    var mapId : String = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpeditionPreparationViewModel::class.java)) {
            return ExpeditionPreparationViewModel(
                suppliesRepository,
                startExpeditionUseCase,
                mapId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}