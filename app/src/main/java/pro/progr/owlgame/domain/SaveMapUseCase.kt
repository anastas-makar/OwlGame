package pro.progr.owlgame.domain

import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.web.Map
import javax.inject.Inject

class SaveMapUseCase @Inject constructor(val mapsRepository: MapsRepository) {
    operator fun invoke(map: Map) {
        //todo: сохранение картинки в хранилище
        mapsRepository.saveMap(map)
    }
}