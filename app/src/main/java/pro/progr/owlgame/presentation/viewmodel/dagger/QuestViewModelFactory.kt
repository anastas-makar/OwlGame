package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.diagnostics.Diagnostics
import pro.progr.owlgame.domain.repository.LocationsRepository
import pro.progr.owlgame.domain.repository.LootRepository
import pro.progr.owlgame.domain.repository.QuestsRepository
import pro.progr.owlgame.domain.usecase.SavePouchUseCase
import pro.progr.owlgame.presentation.viewmodel.QuestViewModel
import javax.inject.Inject

class QuestViewModelFactory @Inject constructor(
    private val questsRepository: QuestsRepository,
    private val lootRepository: LootRepository,
    private val locationsRepository: LocationsRepository,
    private val savePouchUseCase: SavePouchUseCase,
    private val diagnostics: Diagnostics,
) : ViewModelProvider.Factory {

    var questId: String = ""
    var locationSceneId = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestViewModel::class.java)) {
            return QuestViewModel(
                questsRepository = questsRepository,
                questId = questId,
                locationSceneId = locationSceneId,
                lootRepository = lootRepository,
                locationsRepository = locationsRepository,
                savePouchUseCase = savePouchUseCase,
                diagnostics = diagnostics) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
