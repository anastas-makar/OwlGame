package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pro.progr.owlgame.data.db.entity.LocationScene

@Dao
interface LocationScenesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(locationScenes: List<LocationScene>): List<Long>

    @Query(
        """
        UPDATE location_scenes
        SET imageUrl = :imageUrl,
            description = :description,
            imageKey = :imageKey,
            questId = NULL,
            questButtonText = NULL
        WHERE id = :sceneId
        """
    )
    suspend fun applyQuestResult(
        sceneId: String,
        imageUrl: String,
        imageKey: String,
        description: String
    )
}
