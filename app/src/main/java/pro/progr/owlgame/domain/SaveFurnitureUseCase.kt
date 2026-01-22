package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.Furniture
import pro.progr.owlgame.data.repository.FurnitureRepository
import pro.progr.owlgame.data.repository.ImageRepository
import pro.progr.owlgame.data.web.inpouch.FurnitureInPouch
import javax.inject.Inject

class SaveFurnitureUseCase @Inject constructor(private val furnitureRepository: FurnitureRepository,
                                               private val imageRepository: ImageRepository) {
    suspend operator fun invoke(furnitureInPouch: List<FurnitureInPouch>): List<FurnitureInPouch> {
        val furnitureConverted = furnitureInPouch.map {
            it.copy(
                imageUrl = imageRepository.saveImageLocally(it.imageUrl)
            )
        }

        furnitureRepository.insert(
            furnitureConverted.map { pConv ->
                Furniture(
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
