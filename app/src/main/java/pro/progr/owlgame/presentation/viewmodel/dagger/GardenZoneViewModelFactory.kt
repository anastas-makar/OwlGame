package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.db.GardenType
import pro.progr.owlgame.data.repository.BuildingsRepository
import pro.progr.owlgame.data.repository.GardenItemsRepository
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.repository.PlantsRepository
import pro.progr.owlgame.data.repository.SlotsRepository
import pro.progr.owlgame.domain.FoundTownUseCase
import pro.progr.owlgame.presentation.viewmodel.BuildingViewModel
import pro.progr.owlgame.presentation.viewmodel.GardenZoneViewModel
import pro.progr.owlgame.presentation.viewmodel.MapViewModel
import javax.inject.Inject

class GardenZoneViewModelFactory @Inject constructor(
    private val gardenItemsRepo: GardenItemsRepository,
    private val plantsRepo: PlantsRepository
) : ViewModelProvider.Factory {
    var gardenId: String = ""
    var gardenType: GardenType = GardenType.GARDEN

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GardenZoneViewModel::class.java)) {
            return GardenZoneViewModel(
                gardenItemsRepo,
                plantsRepo,
                gardenId,
                gardenType) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}