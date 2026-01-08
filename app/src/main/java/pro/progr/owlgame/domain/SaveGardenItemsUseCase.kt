package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.GardenItem
import pro.progr.owlgame.data.repository.GardenItemsRepository
import pro.progr.owlgame.data.repository.ImageRepository
import pro.progr.owlgame.data.web.inpouch.GardenItemInPouch
import javax.inject.Inject

class SaveGardenItemsUseCase @Inject constructor(private val gardenItemsRepository: GardenItemsRepository,
                                                 private val imageRepository: ImageRepository) {
    suspend operator fun invoke(gardenItemsInPouch: List<GardenItemInPouch>): List<GardenItemInPouch> {
        val gardenItemsConverted = gardenItemsInPouch.map {
            it.copy(
                imageUrl = imageRepository.saveImageLocally(it.imageUrl)
            )
        }

        gardenItemsRepository.insert(
            gardenItemsConverted.map { gI ->
                GardenItem(
                    id = gI.id,
                    name = gI.name,
                    imageUrl = gI.imageUrl,
                    description = gI.description,
                    supplyName = gI.supplyName,
                    supplyAmount = gI.supplyAmount,
                    itemType = gI.itemType,
                    gardenType = gI.gardenType
                )
            }
        )

        return gardenItemsConverted
    }
}
