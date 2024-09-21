package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import pro.progr.owlgame.data.repository.TownRepository
import javax.inject.Inject

class TownViewModel @Inject constructor(
    private val townRepository: TownRepository
) : ViewModel() {
    fun getTownName() : String {
        return townRepository.getName()
    }
}