package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.repository.ImageRepository
import pro.progr.owlgame.domain.repository.PlantsRepository
import pro.progr.owlgame.domain.repository.SuppliesRepository
import pro.progr.owlgame.domain.model.PlantModel
import pro.progr.owlgame.domain.model.PlantWithSupplyModel
import pro.progr.owlgame.domain.model.SupplyModel
import javax.inject.Inject

class SavePlantsUseCase @Inject constructor(private val plantsRepository: PlantsRepository,
                                            private val imageRepository: ImageRepository,
                                            private val suppliesRepository: SuppliesRepository) {
    suspend operator fun invoke(plantsInPouch: List<PlantWithSupplyModel>): List<PlantWithSupplyModel> {
        val plantsConverted = plantsInPouch.map {
            it.copy(
                imageUrl = imageRepository.saveImageLocally(
                    imageUrl = it.imageUrl,
                    imageKey = it.imageKey),
                supply = it.supply.copy(
                    imageUrl = imageRepository.saveImageLocally(
                        imageUrl = it.supply.imageUrl,
                        imageKey = it.supply.imageKey)
                )
            )
        }

        suppliesRepository.insert(
            plantsConverted.map { pConv ->
                SupplyModel(
                    id = pConv.supply.id,
                    imageUrl = pConv.supply.imageUrl,
                    name = pConv.supply.name,
                    description = pConv.supply.description,
                    amount = 0,
                    effectType = pConv.supply.effectType,
                    effectAmount = pConv.supply.effectAmount,
                    templateId = pConv.supply.templateId,
                    imageKey = pConv.supply.imageKey
                )
            }
        )

        plantsRepository.insert(
            plantsConverted.map { pConv ->
                PlantModel(
                    id = pConv.id,
                    name = pConv.name,
                    description = pConv.description,
                    imageUrl = pConv.imageUrl,
                    supplyId = pConv.supply.id,
                    supplyAmount = pConv.supplyAmount,
                    seedAmount = pConv.seedAmount,
                    templateId = pConv.templateId,
                    imageKey = pConv.imageKey
                )
            }
        )

        return plantsConverted
    }
}
