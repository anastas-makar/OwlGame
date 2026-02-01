package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.Plant
import pro.progr.owlgame.data.db.Supply
import pro.progr.owlgame.data.repository.ImageRepository
import pro.progr.owlgame.data.repository.PlantsRepository
import pro.progr.owlgame.data.repository.SuppliesRepository
import pro.progr.owlgame.data.web.inpouch.PlantInPouch
import javax.inject.Inject

class SavePlantsUseCase @Inject constructor(private val plantsRepository: PlantsRepository,
                                            private val imageRepository: ImageRepository,
                                            private val suppliesRepository: SuppliesRepository) {
    suspend operator fun invoke(plantsInPouch: List<PlantInPouch>): List<PlantInPouch> {
        val plantsConverted = plantsInPouch.map {
            it.copy(
                imageUrl = imageRepository.saveImageLocally(it.imageUrl),
                supply = it.supply.copy(
                    imageUrl = imageRepository.saveImageLocally(it.supply.imageUrl)
                )
            )
        }

        plantsRepository.insert(
            plantsConverted.map { pConv ->
                Plant(
                    id = pConv.id,
                    name = pConv.name,
                    description = pConv.description,
                    imageUrl = pConv.imageUrl,
                    supplyId = pConv.supply.id,
                    supplyAmount = pConv.supplyAmount,
                    seedAmount = pConv.seedAmount
                )
            }
        )

        suppliesRepository.insert(
            plantsConverted.map { pConv ->
                Supply(
                    id = pConv.supply.id,
                    imageUrl = pConv.supply.imageUrl,
                    name = pConv.supply.name,
                    amount = 0,
                    effectType = pConv.supply.effectType,
                    effectAmount = pConv.supply.effectAmount
                )
            }
        )

        return plantsConverted
    }
}
