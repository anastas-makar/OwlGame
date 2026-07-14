package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.domain.repository.LootRepository
import pro.progr.owlgame.domain.repository.QuestsRepository
import pro.progr.owlgame.domain.usecase.SavePouchUseCase
import pro.progr.owlgame.presentation.viewmodel.QuestViewModel
import javax.inject.Inject

class QuestViewModelFactory @Inject constructor(
    private val questsRepository: QuestsRepository,
    private val lootRepository: LootRepository,
    private val savePouchUseCase: SavePouchUseCase,
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
                savePouchUseCase = savePouchUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}