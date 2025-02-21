package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.repository.ImageRepository
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.web.inpouch.MapInPouchModel
import javax.inject.Inject

class SaveMapsUseCase @Inject constructor(private val mapsRepository: MapsRepository,
    private val imageRepository: ImageRepository) {
    suspend operator fun invoke(maps: List<MapInPouchModel>): List<MapEntity> {
        val mapsForLocal = maps.map {
            MapEntity(
                id = it.id,
                name = it.name,
                imagePath = imageRepository.saveImageLocally(it.imageUrl)
            )
        }

        mapsRepository.saveMaps(mapsForLocal)

        return mapsForLocal
    }
}
