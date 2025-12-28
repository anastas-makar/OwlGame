package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.Building
import pro.progr.owlgame.data.db.Garden
import pro.progr.owlgame.data.db.RoomEntity
import pro.progr.owlgame.data.repository.BuildingsRepository
import pro.progr.owlgame.data.repository.ImageRepository
import pro.progr.owlgame.data.web.inpouch.BuildingInPouch
import javax.inject.Inject

class SaveBuildingsUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val buildingsRepository: BuildingsRepository
) {
    suspend operator fun invoke(buildings: List<BuildingInPouch>): List<Building> {

        val buildingsForLocal = buildings.map { b ->
            Building(
                id = b.id,
                name = b.name,
                imageUrl = imageRepository.saveImageLocally(b.imageUrl),
                price = b.cost,
                type = b.type
            )
        }

        val gardensForLocal = buildings.flatMap { b ->
            b.gardens.map { g ->
                Garden(
                    id = g.id,
                    name = g.name,
                    imageUrl = imageRepository.saveImageLocally(g.imageUrl),
                    buildingId = b.id,
                    gardenNumber = g.gardenNumber,
                    gardenType = g.gardenType
                )
            }
        }

        val roomsForLocal = buildings.flatMap { b ->
            b.rooms.map { r ->
                RoomEntity(
                    id = r.id,
                    name = r.name,
                    imageUrl = imageRepository.saveImageLocally(r.imageUrl),
                    buildingId = b.id,
                    roomNumber = r.roomNumber
                )
            }
        }

        buildingsRepository.saveBuildingsBundle(
            buildings = buildingsForLocal,
            gardens = gardensForLocal,
            rooms = roomsForLocal
        )

        return buildingsForLocal
    }
}

