package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.model.FurnitureModel
import pro.progr.owlgame.domain.repository.FurnitureRepository
import pro.progr.owlgame.domain.repository.ImageRepository
import javax.inject.Inject

class SaveFurnitureUseCase @Inject constructor(private val furnitureRepository: FurnitureRepository,
                                               private val imageRepository: ImageRepository) {
    suspend operator fun invoke(furnitureInPouch: List<FurnitureModel>): List<FurnitureModel> {
        val furnitureConverted = furnitureInPouch.map {
            it.copy(
                imageUrl = imageRepository.saveImageLocally(it.imageUrl)
            )
        }

        furnitureRepository.insert(
            furnitureConverted.map { pConv ->
                FurnitureModel(
                    id = pConv.id,
                    name = pConv.name,
                    imageUrl = pConv.imageUrl,
                    price = pConv.price,
                    height = pConv.height,
                    width = pConv.width,
                    type = pConv.type
                )
            }
        )

        return furnitureConverted
    }
}
