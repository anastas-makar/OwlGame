package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pro.progr.owlgame.data.db.BuildingWithData
import pro.progr.owlgame.data.repository.BuildingsRepository
import pro.progr.owlgame.domain.ApplyOfflineGrowthUseCase
import javax.inject.Inject

class BuildingViewModel @Inject constructor(
    private val buildingsRepository: BuildingsRepository,
    private val applyOfflineGrowthUseCase: ApplyOfflineGrowthUseCase,
    private val buildingId: String
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            applyOfflineGrowthUseCase()
        }
    }

    fun observe(): Flow<BuildingWithData> =
        buildingsRepository.observe(buildingId)
}