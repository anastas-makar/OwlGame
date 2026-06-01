package pro.progr.owlgame.domain.usecase

import pro.progr.owlgame.domain.model.FurnitureModel
import pro.progr.owlgame.domain.repository.FurnitureRepository
import pro.progr.owlgame.domain.repository.ImageRepository
import javax.inject.Inject

class SaveFurnitureUseCase @Inject constructor(
    private val furnitureRepository: FurnitureRepository,
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(furnitureInPouch: List<FurnitureModel>): List<FurnitureModel> {
        val furnitureConverted = furnitureInPouch.map { furniture ->
            furniture.copy(
                imageUrl = imageRepository.saveImageLocally(furniture.imageUrl)
            )
        }

        furnitureRepository.insert(furnitureConverted)

        return furnitureConverted
    }
}