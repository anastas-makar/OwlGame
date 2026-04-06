package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.data.db.Enemy
import pro.progr.owlgame.data.db.Expedition
import pro.progr.owlgame.data.db.MapEntity
import pro.progr.owlgame.data.db.MapType
import pro.progr.owlgame.domain.repository.ImageRepository
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.data.web.inpouch.MapInPouchModel
import javax.inject.Inject

class SaveMapsUseCase @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(maps: List<MapInPouchModel>): List<MapEntity> {
        val mapEntities = mutableListOf<MapEntity>()
        val expeditionEntities = mutableListOf<Expedition>()
        val enemyEntities = mutableListOf<Enemy>()

        maps.forEach { mapModel ->
            val localImagePath = imageRepository.saveImageLocally(mapModel.imageUrl)

            mapEntities += MapEntity(
                id = mapModel.id,
                name = mapModel.name,
                imagePath = localImagePath,
                type = mapModel.type
            )

            if (mapModel.type == MapType.OCCUPIED) {
                val expeditionModel = requireNotNull(mapModel.expedition) {
                    "Map ${mapModel.id} has type OCCUPIED but expedition is null"
                }

                expeditionEntities += Expedition(
                    id = expeditionModel.id,
                    title = expeditionModel.title,
                    description = expeditionModel.description,
                    mapId = mapModel.id,
                    healAmount = 0,
                    damageAmount = 0,
                    animalId = null
                )

                enemyEntities += expeditionModel.enemies.map { enemyModel ->
                    Enemy(
                        id = enemyModel.id,
                        expeditionId = expeditionModel.id,
                        name = enemyModel.name,
                        description = enemyModel.description,
                        imageUrl = enemyModel.imageUrl,
                        healAmount = enemyModel.healAmount,
                        damageAmount = enemyModel.damageAmount,
                        x = enemyModel.x,
                        y = enemyModel.y
                    )
                }
            }
        }

        mapsRepository.saveMaps(
            maps = mapEntities,
            expeditions = expeditionEntities,
            enemies = enemyEntities
        )

        return mapEntities
    }
}
