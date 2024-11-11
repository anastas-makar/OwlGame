package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import pro.progr.owlgame.data.repository.MapsRepository
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val mapId: String
) : ViewModel() {

    var map = mapsRepository.getMapById(mapId)
}