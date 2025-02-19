package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.repository.BuildingsRepository
import pro.progr.owlgame.data.repository.MapsRepository
import pro.progr.owlgame.data.web.inpouch.BuildingInPouch
import javax.inject.Inject

class SaveBuildingsUseCase @Inject constructor(private val mapsRepository: MapsRepository,
    private val buildingsRepository: BuildingsRepository) {

    //todo: тут, наверное, другие модели, как с картами
    suspend operator fun invoke(maps: List<BuildingInPouch>): List<Building> {
        val buildingsForLocal = maps.map {
            Building(
                id = it.id,
                name = it.name,
                imageUrl = mapsRepository.saveImageLocally(it.imageUrl)
            )
        }

        buildingsRepository.saveBuildings(buildingsForLocal)

        return buildingsForLocal
    }
}
