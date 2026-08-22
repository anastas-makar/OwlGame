package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.model.BuildingWithDataModel
import pro.progr.owlgame.domain.repository.BuildingsRepository
import pro.progr.owlgame.domain.repository.ImageRepository
import javax.inject.Inject

class SaveBuildingsUseCase @Inject constructor(private val buildingsRepository: BuildingsRepository,
                                              private val imageRepository: ImageRepository)  {

    suspend operator fun invoke(buildings: List<BuildingWithDataModel>): List<BuildingWithDataModel> {
        val savedBuildings = buildings.map { building ->
            building.copy(
                imageUrl = imageRepository.saveImageLocally(
                    imageUrl = building.imageUrl,
                    imageKey =  building.imageKey),
                rooms = building.rooms.map { room ->
                    room.copy(
                        imageUrl = imageRepository.saveImageLocally(
                            imageUrl = room.imageUrl,
                            imageKey = building.imageKey)
                    )
                },
                gardens = building.gardens.map { garden ->
                    garden.copy(imageUrl = imageRepository.saveImageLocally(
                        imageUrl = garden.imageUrl,
                        imageKey = garden.imageKey))
                }
            )
        }

        buildingsRepository.saveBuildingsBundle(savedBuildings)

        return savedBuildings
    }
}
