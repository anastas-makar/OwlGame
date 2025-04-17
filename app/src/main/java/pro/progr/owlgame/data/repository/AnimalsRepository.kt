package pro.progr.owlgame.data.repository

import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.db.AnimalDao
import pro.progr.owlgame.data.web.AnimalApiService
import javax.inject.Inject
import javax.inject.Named

class AnimalsRepository @Inject constructor(
    private val animalDao: AnimalDao,
    private val animalApiService: AnimalApiService,
    private val imageRepository: ImageRepository,
    @Named("apiKey") private val apiKey: String
) {
    fun countAnimalsSearching() : Long {
        return animalDao.countSearching()
    }

    suspend fun getAnimal() : Animal? {
        val response =animalApiService.getAnimal(apiKey)

        if (response.isSuccessful) {
            return response.body()
        }

        return null
    }

    suspend fun saveAnimal(animal: Animal) : Animal {
        val savedAnimal = animal.copy(
            imagePath = imageRepository.saveImageLocally(animal.imagePath))
        animalDao.insert(savedAnimal)

        return savedAnimal
    }
}