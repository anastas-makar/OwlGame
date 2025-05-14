package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.repository.BuildingsRepository
import pro.progr.owlgame.data.repository.ImageRepository
import pro.progr.owlgame.data.web.inpouch.BuildingInPouch
import javax.inject.Inject

class SaveBuildingsUseCase @Inject constructor(private val imageRepository: ImageRepository,
    private val buildingsRepository: BuildingsRepository) {

    //todo: тут, наверное, другие модели, как с картами
    suspend operator fun invoke(buildings: List<BuildingInPouch>): List<Building> {
        val buildingsForLocal = buildings.map {
            Building(
                id = it.id,
                name = it.name,
                imageUrl = imageRepository.saveImageLocally(it.imageUrl),
                price = it.cost,
                type = it.type
            )
        }

        buildingsRepository.saveBuildings(buildingsForLocal)

        return buildingsForLocal
    }
}
