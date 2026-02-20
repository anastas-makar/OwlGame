package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.GardenItem
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.repository.GardenItemsRepository
import pro.progr.owlgame.data.repository.ImageRepository
import pro.progr.owlgame.data.repository.SuppliesRepository
import pro.progr.owlgame.data.web.inpouch.GardenItemInPouch
import javax.inject.Inject

class SaveGardenItemsUseCase @Inject constructor(private val gardenItemsRepository: GardenItemsRepository,
                                                 private val imageRepository: ImageRepository,
                                                 private val suppliesRepository: SuppliesRepository) {
    suspend operator fun invoke(gardenItemsInPouch: List<GardenItemInPouch>): List<GardenItemInPouch> {
        val gardenItemsConverted = gardenItemsInPouch.map {
            it.copy(
                imageUrl = imageRepository.saveImageLocally(it.imageUrl),
                supply = it.supply.copy(
                    imageUrl = imageRepository.saveImageLocally(it.supply.imageUrl)
                )
            )
        }

        gardenItemsRepository.insert(
            gardenItemsConverted.map { gI ->
                GardenItem(
                    id = gI.id,
                    name = gI.name,
                    imageUrl = gI.imageUrl,
                    description = gI.description,
                    supplyId = gI.supply.id,
                    supplyAmount = gI.supplyAmount,
                    itemType = gI.itemType,
                    gardenType = gI.gardenType
                )
            }
        )

        suppliesRepository.insert(
            gardenItemsConverted.map { gI ->
                Supply(
                    id = gI.supply.id,
                    imageUrl = gI.supply.imageUrl,
                    name = gI.supply.name,
                    description = gI.supply.description,
                    amount = 0,
                    effectType = gI.supply.effectType,
                    effectAmount = gI.supply.effectAmount
                )
            }
        )

        return gardenItemsConverted
    }
}
