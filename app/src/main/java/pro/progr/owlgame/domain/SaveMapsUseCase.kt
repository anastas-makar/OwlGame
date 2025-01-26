package pro.progr.owlgame.domain

import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.web.Map
import javax.inject.Inject

class SaveMapsUseCase @Inject constructor(val mapsRepository: MapsRepository) {
    operator fun invoke(maps: List<Map>) {
        //todo: сохранение картинок в хранилище
        mapsRepository.saveMaps(maps)
    }
}