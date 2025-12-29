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
    suspend operator fun invoke(buildings: List<BuildingInPouch>): List<BuildingInPouch> {

        // 1) делаем "локальную" копию DTO (для UI мешочка)
        val buildingsWithLocalUrls: List<BuildingInPouch> = buildings.map { b ->
            val buildingUrl = imageRepository.saveImageLocally(b.imageUrl)

            val roomsLocal = b.rooms.map { r ->
                r.copy(imageUrl = imageRepository.saveImageLocally(r.imageUrl))
            }

            val gardensLocal = b.gardens.map { g ->
                g.copy(imageUrl = imageRepository.saveImageLocally(g.imageUrl))
            }

            b.copy(
                imageUrl = buildingUrl,
                rooms = roomsLocal,
                gardens = gardensLocal
            )
        }

        // 2) маппим в entities (используем уже локальные url)
        val buildingsForLocal = buildingsWithLocalUrls.map { b ->
            Building(
                id = b.id,
                name = b.name,
                imageUrl = b.imageUrl,
                price = b.cost,
                type = b.type
            )
        }

        val roomsForLocal = buildingsWithLocalUrls.flatMap { b ->
            b.rooms.map { r ->
                RoomEntity(
                    id = r.id,
                    name = r.name,
                    imageUrl = r.imageUrl,
                    buildingId = b.id,
                    roomNumber = r.roomNumber
                )
            }
        }

        val gardensForLocal = buildingsWithLocalUrls.flatMap { b ->
            b.gardens.map { g ->
                Garden(
                    id = g.id,
                    name = g.name,
                    imageUrl = g.imageUrl,
                    buildingId = b.id,
                    gardenNumber = g.gardenNumber,
                    gardenType = g.gardenType
                )
            }
        }

        // 3) сохраняем одной транзакцией (как ты уже сделал)
        buildingsRepository.saveBuildingsBundle(
            buildings = buildingsForLocal,
            gardens = gardensForLocal,
            rooms = roomsForLocal
        )

        // 4) отдаём UI мешочка готовые DTO с локальными путями
        return buildingsWithLocalUrls
    }
}


