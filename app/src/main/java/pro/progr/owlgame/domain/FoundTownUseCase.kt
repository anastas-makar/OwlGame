package pro.progr.owlgame.domain

import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.repository.StreetsRepository
import javax.inject.Inject

class FoundTownUseCase @Inject constructor(val mapsRepository: MapsRepository,
    val streetsRepository: StreetsRepository) {

    suspend operator fun invoke(mapId : String, name: String, streetName: String) {

        streetsRepository.createStreet(mapId, streetName)

        mapsRepository.setTown(
                name = name,
                mapId = mapId
        )
    }
}