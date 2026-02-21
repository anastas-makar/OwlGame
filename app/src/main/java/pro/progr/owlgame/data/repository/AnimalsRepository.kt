package pro.progr.owlgame.data.repository

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Animal

interface AnimalsRepository {
    fun countAnimalsSearching() : Long

    suspend fun getApiAnimal() : Animal?

    suspend fun findSearchingAnimalInDataBase() : Animal?

    fun getAnimalById(id : String) : Flow<Animal?>

    fun setPet(animalId: String)

    suspend fun saveAnimal(animal: Animal) : Animal
}