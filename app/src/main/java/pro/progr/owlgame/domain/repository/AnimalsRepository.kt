package pro.progr.owlgame.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.AnimalStatus

interface AnimalsRepository {
    fun countAnimalsSearching() : Long

    suspend fun getApiAnimal() : AnimalModel?

    suspend fun findSearchingAnimalInDataBase() : AnimalModel?

    fun getAnimalById(id : String) : Flow<AnimalModel?>

    fun setPet(animalId: String)

    suspend fun saveAnimal(animal: AnimalModel) : AnimalModel

    fun getPets(): Flow<List<AnimalModel>>

    suspend fun getPetsOnce(): List<AnimalModel>

    suspend fun getById(animalId: String): AnimalModel?

    suspend fun updateStatusIfCurrent(
        animalId: String,
        newStatus: AnimalStatus,
        expectedOldStatus: AnimalStatus
    ): Int
}