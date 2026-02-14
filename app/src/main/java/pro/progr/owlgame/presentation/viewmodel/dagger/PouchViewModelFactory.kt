package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.domain.SaveBuildingsUseCase
import pro.progr.owlgame.domain.SaveFurnitureUseCase
import pro.progr.owlgame.domain.SaveGardenItemsUseCase
import pro.progr.owlgame.domain.SaveMapsUseCase
import pro.progr.owlgame.domain.SavePlantsUseCase
import pro.progr.owlgame.domain.SaveReceiptsUseCase
import pro.progr.owlgame.presentation.viewmodel.InPouchViewModel
import pro.progr.owlgame.presentation.viewmodel.PouchesViewModel
import javax.inject.Inject

class PouchViewModelFactory @Inject constructor(
    private val pouchesRepository: PouchesRepository,
    private val saveMapsUseCase: SaveMapsUseCase,
    private val saveBuildingsUseCase: SaveBuildingsUseCase,
    private val savePlantsUseCase: SavePlantsUseCase,
    private val saveGardenItemsUseCase: SaveGardenItemsUseCase,
    private val saveFurnitureUseCase: SaveFurnitureUseCase,
    private val saveReceiptsUseCase: SaveReceiptsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PouchesViewModel::class.java)) {
            return PouchesViewModel(pouchesRepository) as T
        } else if (modelClass.isAssignableFrom(InPouchViewModel::class.java)) {
            return InPouchViewModel(pouchesRepository,
                saveMapsUseCase,
                saveBuildingsUseCase,
                savePlantsUseCase,
                saveGardenItemsUseCase,
                saveFurnitureUseCase,
                saveReceiptsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}