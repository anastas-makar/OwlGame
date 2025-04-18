package pro.progr.owlgame.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "building_to_animal",
            indices = arrayOf(
            Index(value = arrayOf("animal_id"), unique = true),
            Index(value = arrayOf("building_id"), unique = true)
            ))
data class BuildingToAnimal (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "building_id")
    val buildingId: String,

    @ColumnInfo(name = "animal_id")
    val animalId: String

)