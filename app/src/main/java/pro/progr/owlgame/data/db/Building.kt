package pro.progr.owlgame.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "buildings",
    indices = [
        Index(value = ["animalId"], unique = true),
        Index(value = ["mapId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = Animal::class,
            parentColumns = ["id"],
            childColumns = ["animalId"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MapEntity::class,
            parentColumns = ["id"],
            childColumns = ["mapId"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Building (
    @PrimaryKey
    val id : String,
    val name : String,
    val imageUrl : String,
    val mapId : String? = null,
    val price : Int = 500,
    var animalId: String? = null,
    val x : Float = 0f,
    val y : Float = 0f,
    val type: BuildingType = BuildingType.HOUSE
)