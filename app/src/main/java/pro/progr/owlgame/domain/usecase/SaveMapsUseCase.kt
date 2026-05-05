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
                imageUrl = imageRepository.saveImageLocally(model.imageUrl),
                expedition = model.expedition?.copy(
                    enemies = model.expedition.enemies.map {
                        it.copy(
                            imageUrl = imageRepository.saveImageLocally(it.imageUrl)
                        )
                    },
                    medal = model.expedition.medal.copy(
                        imageUrl = imageRepository.saveImageLocally(model.expedition.medal.imageUrl)
                    )
                )
            )
        }

        mapsRepository.saveMaps(
            convertedMaps
        )

        return convertedMaps
    }
}
