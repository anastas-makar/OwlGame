package pro.progr.owlgame.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pro.progr.owlgame.data.db.entity.*

@Dao
interface GameSyncDao {
    @Query("SELECT * FROM animals WHERE id IN (:ids)")
    suspend fun getAnimals(ids: List<String>): List<Animal>

    @Query("SELECT * FROM buildings WHERE id IN (:ids)")
    suspend fun getBuildings(ids: List<String>): List<Building>

    @Query("SELECT * FROM countries WHERE id IN (:ids)")
    suspend fun getCountries(ids: List<String>): List<Country>

    @Query("SELECT * FROM enemies WHERE id IN (:ids)")
    suspend fun getEnemies(ids: List<String>): List<Enemy>

    @Query("SELECT * FROM expeditions WHERE id IN (:ids)")
    suspend fun getExpeditions(ids: List<String>): List<Expedition>

    @Query("SELECT * FROM expedition_medals WHERE id IN (:ids)")
    suspend fun getExpeditionMedals(ids: List<String>): List<ExpeditionMedal>

    @Query("SELECT * FROM furniture WHERE id IN (:ids)")
    suspend fun getFurniture(ids: List<String>): List<Furniture>

    @Query("SELECT * FROM gardens WHERE id IN (:ids)")
    suspend fun getGardens(ids: List<String>): List<Garden>

    @Query("SELECT * FROM garden_items WHERE id IN (:ids)")
    suspend fun getGardenItems(ids: List<String>): List<GardenItem>

    @Query("SELECT * FROM locations WHERE id IN (:ids)")
    suspend fun getLocations(ids: List<String>): List<Location>

    @Query("SELECT * FROM location_scenes WHERE id IN (:ids)")
    suspend fun getLocationScenes(ids: List<String>): List<LocationScene>

    @Query("SELECT * FROM maps WHERE id IN (:ids)")
    suspend fun getMaps(ids: List<String>): List<MapEntity>

    @Query("SELECT * FROM plants WHERE id IN (:ids)")
    suspend fun getPlants(ids: List<String>): List<Plant>

    @Query("SELECT * FROM recipes WHERE id IN (:ids)")
    suspend fun getRecipes(ids: List<String>): List<Recipe>

    @Query("SELECT * FROM rooms WHERE id IN (:ids)")
    suspend fun getRooms(ids: List<String>): List<RoomEntity>

    @Query("SELECT * FROM streets WHERE id IN (:ids)")
    suspend fun getStreets(ids: List<String>): List<Street>

    @Query("SELECT * FROM supplies WHERE id IN (:ids)")
    suspend fun getSupplies(ids: List<String>): List<Supply>

    @Query("SELECT * FROM supply_to_recipe WHERE id IN (:ids)")
    suspend fun getSupplyToRecipes(ids: List<String>): List<SupplyToRecipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(items: List<Country>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimals(items: List<Animal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaps(items: List<MapEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStreets(items: List<Street>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuildings(items: List<Building>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRooms(items: List<RoomEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGardens(items: List<Garden>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupplies(items: List<Supply>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(items: List<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupplyToRecipes(items: List<SupplyToRecipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlants(items: List<Plant>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGardenItems(items: List<GardenItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFurniture(items: List<Furniture>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(items: List<Location>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationScenes(items: List<LocationScene>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpeditions(items: List<Expedition>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnemies(items: List<Enemy>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpeditionMedals(items: List<ExpeditionMedal>)
}
