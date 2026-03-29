package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.db.AnimalStatus

interface AnimalsRepository {
    fun countAnimalsSearching() : Long

    suspend fun getApiAnimal() : Animal?

    suspend fun findSearchingAnimalInDataBase() : Animal?

    fun getAnimalById(id : String) : Flow<Animal?>

    fun setPet(animalId: String)

    suspend fun saveAnimal(animal: Animal) : Animal

    fun getPets(): Flow<List<Animal>>

    suspend fun getPetsOnce(): List<Animal>

    suspend fun getById(animalId: String): Animal?

    suspend fun updateStatusIfCurrent(
        animalId: String,
        newStatus: AnimalStatus,
        expectedOldStatus: AnimalStatus
    ): Int
}