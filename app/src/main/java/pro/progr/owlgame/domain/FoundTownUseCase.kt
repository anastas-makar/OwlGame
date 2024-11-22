package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.Town
import pro.progr.owlgame.data.repository.TownRepository
import pro.progr.owlgame.data.web.Map
import javax.inject.Inject

class FoundTownUseCase @Inject constructor(val townRepository: TownRepository) {
    operator fun invoke(map: Map, name: String) {
        //todo: сохранение карты
        val mapId = map.id

        townRepository.insertTown(
            Town(
                name = name,
                mapId = mapId
            )
        )
    }
}