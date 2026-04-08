package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.data.db.entity.Enemy
import pro.progr.owlgame.data.db.entity.Expedition
import pro.progr.owlgame.data.db.entity.MapEntity
import pro.progr.owlgame.data.db.model.MapType
import pro.progr.owlgame.domain.model.MapInPouchModel
import pro.progr.owlgame.domain.repository.ImageRepository
import pro.progr.owlgame.domain.repository.MapsRepository
import javax.inject.Inject

class SaveMapsUseCase @Inject constructor(
    private val mapsRepository: MapsRepository,
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(maps: List<MapInPouchModel>): List<MapEntity> {


        mapsRepository.saveMaps(
            maps = mapEntities,
            expeditions = expeditionEntities,
            enemies = enemyEntities
        )

        return mapEntities
    }
}
