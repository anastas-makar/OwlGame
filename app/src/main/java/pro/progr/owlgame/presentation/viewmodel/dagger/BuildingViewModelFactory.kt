package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.BuildingsRepository
import pro.progr.owlgame.domain.ApplyOfflineGrowthUseCase
import pro.progr.owlgame.presentation.viewmodel.BuildingViewModel
import javax.inject.Inject

class BuildingViewModelFactory @Inject constructor(
    private val buildingsRepository: BuildingsRepository,
    private val applyOfflineGrowthUseCase: ApplyOfflineGrowthUseCase,
) : ViewModelProvider.Factory {
    var id : String = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BuildingViewModel::class.java)) {
            return BuildingViewModel(
                buildingsRepository,
                applyOfflineGrowthUseCase,
                id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}