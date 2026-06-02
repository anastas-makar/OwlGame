package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.model.LocationWithScenesModel
import pro.progr.owlgame.domain.repository.ImageRepository
import pro.progr.owlgame.domain.repository.LocationsRepository
import javax.inject.Inject

class SaveLocationsUseCase @Inject constructor(private val locationsRepository: LocationsRepository,
                                               private val imageRepository: ImageRepository) {
    suspend operator fun invoke(locationsInPouch: List<LocationWithScenesModel>): List<LocationWithScenesModel> {
        val locationsConverted = locationsInPouch.map {
            it.copy(
                imageUrl = imageRepository.saveImageLocally(it.imageUrl)
            )
        }

        locationsRepository.insert(
            locationsConverted
        )

        return locationsConverted
    }
}
