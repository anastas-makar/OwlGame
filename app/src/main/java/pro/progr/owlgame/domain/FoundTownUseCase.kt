package pro.progr.owlgame.domain

import androidx.room.Transaction
import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.repository.StreetsRepository
import pro.progr.owlgame.presentation.ui.model.MapData
import javax.inject.Inject

class FoundTownUseCase @Inject constructor(val mapsRepository: MapsRepository,
    val streetsRepository: StreetsRepository) {

    @Transaction
    suspend operator fun invoke(mapId : String, name: String, streetName: String) {

        streetsRepository.createStreet(mapId, streetName)

        mapsRepository.setTown(
                name = name,
                mapId = mapId
        )
    }
}