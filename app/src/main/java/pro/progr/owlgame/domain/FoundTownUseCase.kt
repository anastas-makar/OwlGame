package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.Town
import pro.progr.owlgame.data.repository.TownRepository
import pro.progr.owlgame.presentation.ui.model.MapData
import javax.inject.Inject

class FoundTownUseCase @Inject constructor(val townRepository: TownRepository) {
    operator fun invoke(map: MapData, name: String) : Town {
        val mapId = map.id

        return townRepository.insertTown(
            Town(
                name = name,
                mapId = mapId
            )
        )
    }
}