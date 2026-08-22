package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.model.MapWithDataModel
import pro.progr.owlgame.domain.repository.ImageRepository
import pro.progr.owlgame.domain.repository.MapsRepository
import javax.inject.Inject

class SaveMapsUseCase @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(maps: List<MapWithDataModel>): List<MapWithDataModel> {
        val convertedMaps = maps.map { model ->
            model.copy(
                imageUrl = imageRepository.saveImageLocally(
                    imageUrl = model.imageUrl,
                    imageKey = model.imageKey),
                expedition = model.expedition?.copy(
                    enemies = model.expedition.enemies.map {
                        it.copy(
                            imageUrl = imageRepository.saveImageLocally(
                                imageUrl = it.imageUrl,
                                imageKey = it.imageKey)
                        )
                    },
                    medal = model.expedition.medal.copy(
                        imageUrl = imageRepository.saveImageLocally(
                            imageUrl = model.expedition.medal.imageUrl,
                            imageKey = model.expedition.medal.imageKey)
                    )
                ),
                locations = model.locations.map { location ->
                    location.copy(
                        imageUrl = imageRepository.saveImageLocally(
                            imageUrl = location.imageUrl,
                            imageKey = location.imageKey),
                        scenes = location.scenes.map { scene ->
                            scene.copy(
                                imageUrl = imageRepository.saveImageLocally(
                                    imageUrl = scene.imageUrl,
                                    imageKey = scene.imageKey)
                            )
                        }
                    )
                }
            )
        }

        mapsRepository.saveMaps(
            convertedMaps
        )

        return convertedMaps
    }
}
