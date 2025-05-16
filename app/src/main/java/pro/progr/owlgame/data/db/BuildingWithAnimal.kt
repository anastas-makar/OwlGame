package pro.progr.owlgame.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class BuildingWithAnimal(
    @Embedded val building: Building,
    @Relation(
        parentColumn = "animalId",
        entity = Animal::class,
        entityColumn = "id"
    )
    val animal: Animal?
)