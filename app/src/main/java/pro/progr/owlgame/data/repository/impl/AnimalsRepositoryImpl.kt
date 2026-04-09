package pro.progr.owlgame.data.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pro.progr.owlgame.data.db.dao.AnimalDao
import pro.progr.owlgame.data.mapper.toData
import pro.progr.owlgame.data.mapper.toDomain
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.ImageRepository
import pro.progr.owlgame.data.web.AnimalApiService
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.AnimalStatus as DomainAnimalStatus
import pro.progr.owlgame.data.db.model.AnimalStatus as DbAnimalStatus
import javax.inject.Inject

class AnimalsRepositoryImpl @Inject constructor(
    private val animalDao: AnimalDao,
    private val animalApiService: AnimalApiService,
    private val imageRepository: ImageRepository
) : AnimalsRepository {
    override fun countAnimalsSearching() : Long {
        return animalDao.countSearching()
    }

    override suspend fun getApiAnimal() : AnimalModel? {
        val response = animalApiService.getAnimal()

        if (response.isSuccessful) {
            return response.body()?.toDomain()
        }

        return null
    }

    override suspend fun findSearchingAnimalInDataBase() : AnimalModel? {
        return animalDao.getSearchingAnimal()?.toDomain()
    }

    override fun getAnimalById(id : String) : Flow<AnimalModel?> {
        return animalDao.getById(id).map { it?.toDomain() }
    }

    override fun setPet(animalId: String) {
        animalDao.setPet(animalId)
    }

    override suspend fun saveAnimal(animal: AnimalModel) : AnimalModel {
        val savedAnimal = animal.copy(
            imagePath = imageRepository.saveImageLocally(animal.imagePath))
        animalDao.insert(savedAnimal.toData())

        return savedAnimal
    }

    override fun getPets(): Flow<List<AnimalModel>> {
        return animalDao.getAnimalsByStatus(DbAnimalStatus.PET).map { it.map { it.toDomain() } }
    }

    override suspend fun getPetsOnce(): List<AnimalModel> {
        return animalDao.getAnimalsByStatusOnce(DbAnimalStatus.PET).map { it.toDomain() }
    }

    override suspend fun getById(animalId: String): AnimalModel? {
        return animalDao.getAnimalById(animalId)?.toDomain()
    }

    override suspend fun updateStatusIfCurrent(
        animalId: String,
        newStatus: DomainAnimalStatus,
        expectedOldStatus: DomainAnimalStatus
    ): Int {
        return animalDao.updateStatusIfCurrent(
            animalId,
            newStatus.toData(),
            expectedOldStatus.toData()
        )
    }
}