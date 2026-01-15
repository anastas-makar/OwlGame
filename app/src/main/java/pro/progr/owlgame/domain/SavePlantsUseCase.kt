package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.Plant
import pro.progr.owlgame.data.repository.ImageRepository
import pro.progr.owlgame.data.repository.PlantsRepository
import pro.progr.owlgame.data.web.inpouch.PlantInPouch
import javax.inject.Inject

class SavePlantsUseCase @Inject constructor(private val plantsRepository: PlantsRepository,
                                            private val imageRepository: ImageRepository) {
    suspend operator fun invoke(plantsInPouch: List<PlantInPouch>): List<PlantInPouch> {
        val plantsConverted = plantsInPouch.map {
            it.copy(
                imageUrl = imageRepository.saveImageLocally(it.imageUrl)
            )
        }

        plantsRepository.insert(
            plantsConverted.map { pConv ->
                Plant(
                    id = pConv.id,
                    name = pConv.name,
                    description = pConv.description,
                    imageUrl = pConv.imageUrl,
                    supplyName = pConv.supplyName,
                    supplyAmount = pConv.supplyAmount,
                    seedName = pConv.seedName,
                    seedAmount = pConv.seedAmount,
                    seedImageUrl = pConv.seedImageUrl
                )
            }
        )

        return plantsConverted
    }
}
