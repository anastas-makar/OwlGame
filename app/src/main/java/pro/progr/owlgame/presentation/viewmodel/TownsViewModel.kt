package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.repository.TownRepository
import javax.inject.Inject

class TownsViewModel @Inject constructor(
    private val townRepository: TownRepository,
    private val mapsRepository: MapsRepository
) : ViewModel() {

    var townsList = townRepository.getTownsList()
}