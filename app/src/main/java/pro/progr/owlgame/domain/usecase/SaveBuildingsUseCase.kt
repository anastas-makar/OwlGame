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
                imageUrl = imageRepository.saveImageLocally(building.imageUrl),
                rooms = building.rooms.map { room ->
                    room.copy(
                        imageUrl = imageRepository.saveImageLocally(room.imageUrl)
                    )
                },
                gardens = building.gardens.map { garden ->
                    garden.copy(imageUrl = imageRepository.saveImageLocally(garden.imageUrl))
                }
            )
        }

        buildingsRepository.saveBuildingsBundle(savedBuildings)

        return savedBuildings
    }
}