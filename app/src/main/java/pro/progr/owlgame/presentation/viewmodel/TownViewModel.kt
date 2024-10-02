package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import pro.progr.owlgame.data.repository.TownRepository
import javax.inject.Inject

class TownViewModel @Inject constructor(
    private val townRepository: TownRepository,
    private val townId: Int
) : ViewModel() {

    var town = townRepository.getTownById(townId)
}