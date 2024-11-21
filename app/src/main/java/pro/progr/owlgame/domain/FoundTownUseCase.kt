package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.Town
import pro.progr.owlgame.data.repository.TownRepository
import pro.progr.owlgame.data.web.Map

class FoundTownUseCase(val townRepository: TownRepository) {
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