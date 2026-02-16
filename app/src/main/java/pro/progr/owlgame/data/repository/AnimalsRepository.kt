package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.db.AnimalDao
import pro.progr.owlgame.data.web.AnimalApiService
import javax.inject.Inject

class AnimalsRepository @Inject constructor(
    private val animalDao: AnimalDao,
    private val animalApiService: AnimalApiService,
    private val imageRepository: ImageRepository
) {
    fun countAnimalsSearching() : Long {
        return animalDao.countSearching()
    }

    suspend fun getApiAnimal() : Animal? {
        val response = animalApiService.getAnimal()

        if (response.isSuccessful) {
            return response.body()
        }

        return null
    }

    suspend fun findSearchingAnimalInDataBase() : Animal? {
        return animalDao.getSearchingAnimal()
    }

    fun getAnimalById(id : String) : Flow<Animal?> {
        return animalDao.getById(id)
    }

    fun setPet(animalId: String) {
        animalDao.setPet(animalId)
    }

    suspend fun saveAnimal(animal: Animal) : Animal {
        val savedAnimal = animal.copy(
            imagePath = imageRepository.saveImageLocally(animal.imagePath))
        animalDao.insert(savedAnimal)

        return savedAnimal
    }
}