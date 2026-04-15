package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.model.GardenItemModel
import pro.progr.owlgame.domain.repository.GardenItemsRepository
import pro.progr.owlgame.domain.repository.ImageRepository
import pro.progr.owlgame.domain.repository.SuppliesRepository
import pro.progr.owlgame.domain.model.GardenItemWithSupplyModel
import javax.inject.Inject

class SaveGardenItemsUseCase @Inject constructor(private val gardenItemsRepository: GardenItemsRepository,
                                                 private val imageRepository: ImageRepository,
                                                 private val suppliesRepository: SuppliesRepository) {
    suspend operator fun invoke(gardenItemsInPouch: List<GardenItemWithSupplyModel>): List<GardenItemWithSupplyModel> {
        val gardenItemsConverted : List<GardenItemWithSupplyModel> = gardenItemsInPouch.map {
            it.copy(
                imageUrl = imageRepository.saveImageLocally(it.imageUrl),
                supply = it.supply.copy(
                    imageUrl = imageRepository.saveImageLocally(it.supply.imageUrl)
                )
            )
        }

        suppliesRepository.insert(
            gardenItemsConverted.map { gI -> gI.supply}
        )

        gardenItemsRepository.insert(
            gardenItemsConverted.map { gI ->
                GardenItemModel(
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

        return gardenItemsConverted
    }
}
