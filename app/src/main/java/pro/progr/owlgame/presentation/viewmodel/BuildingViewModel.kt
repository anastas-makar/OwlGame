package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.BuildingWithData
import pro.progr.owlgame.data.repository.BuildingsRepository
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.repository.SlotsRepository
import pro.progr.owlgame.domain.FoundTownUseCase
import javax.inject.Inject

class BuildingViewModel @Inject constructor(
    private val buildingsRepository: BuildingsRepository,
    private val buildingId: String
) : ViewModel() {


    fun observe(): Flow<BuildingWithData> =
        buildingsRepository.observe(buildingId)
}