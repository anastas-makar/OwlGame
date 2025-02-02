package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.web.inpouch.MapInPouchModel
import javax.inject.Inject

class SaveMapsUseCase @Inject constructor(private val mapsRepository: MapsRepository) {
    suspend operator fun invoke(maps: List<MapInPouchModel>): List<MapEntity> {
        var mapsForLocal = maps.map {
            MapEntity(
                id = it.id,
                name = it.name,
                imagePath = mapsRepository.saveImageLocally(it.imageUrl)
            )
        }

        mapsRepository.saveMaps(mapsForLocal)

        return mapsForLocal
    }
}
