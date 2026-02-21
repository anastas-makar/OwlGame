package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.db.AnimalDao
import pro.progr.owlgame.data.repository.AnimalsRepository
import pro.progr.owlgame.data.repository.ImageRepository
import pro.progr.owlgame.data.web.AnimalApiService
import javax.inject.Inject

class AnimalsRepositoryImpl @Inject constructor(
    private val animalDao: AnimalDao,
    private val animalApiService: AnimalApiService,
    private val imageRepository: ImageRepository
) : AnimalsRepository {
    override fun countAnimalsSearching() : Long {
        return animalDao.countSearching()
    }

    override suspend fun getApiAnimal() : Animal? {
        val response = animalApiService.getAnimal()

        if (response.isSuccessful) {
            return response.body()
        }

        return null
    }

    override suspend fun findSearchingAnimalInDataBase() : Animal? {
        return animalDao.getSearchingAnimal()
    }

    override fun getAnimalById(id : String) : Flow<Animal?> {
        return animalDao.getById(id)
    }

    override fun setPet(animalId: String) {
        animalDao.setPet(animalId)
    }

    override suspend fun saveAnimal(animal: Animal) : Animal {
        val savedAnimal = animal.copy(
            imagePath = imageRepository.saveImageLocally(animal.imagePath))
        animalDao.insert(savedAnimal)

        return savedAnimal
    }
}