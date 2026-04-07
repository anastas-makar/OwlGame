package pro.progr.owlgame.data.db.embedded

import androidx.room.Embedded
import androidx.room.Relation
import pro.progr.owlgame.data.db.entity.Animal
import pro.progr.owlgame.data.db.entity.Building

data class BuildingWithAnimal(
    @Embedded val building: Building,
    @Relation(
        parentColumn = "animalId",
        entity = Animal::class,
        entityColumn = "id"
    )
    val animal: Animal?
)