package pro.progr.owlgame.dagger

import dagger.Binds
import dagger.Module
import javax.inject.Singleton
import pro.progr.owlgame.data.repository.*
import pro.progr.owlgame.data.repository.impl.*

@Module
interface RepositoryBindingsModule {

    @Binds
    @Singleton
    fun bindImageRepository(impl: ImageRepositoryImpl): ImageRepository

    @Binds
    @Singleton
    fun bindPouchesRepository(impl: PouchesRepositoryImpl): PouchesRepository

    @Binds
    @Singleton
    fun bindBuildingsRepository(impl: BuildingsRepositoryImpl): BuildingsRepository

    @Binds
    @Singleton
    fun bindPlantsRepository(impl: PlantsRepositoryImpl): PlantsRepository

    @Binds
    @Singleton
    fun bindSuppliesRepository(impl: SuppliesRepositoryImpl): SuppliesRepository

    @Binds
    @Singleton
    fun bindGardenItemsRepository(impl: GardenItemsRepositoryImpl): GardenItemsRepository

    @Binds
    @Singleton
    fun bindFurnitureRepository(impl: FurnitureRepositoryImpl): FurnitureRepository

    @Binds
    @Singleton
    fun bindSupplyToRecipeRepository(impl: SupplyToRecipeRepositoryImpl): SupplyToRecipeRepository

    @Binds
    @Singleton
    fun bindSlotsRepository(impl: SlotsRepositoryImpl): SlotsRepository

    @Binds
    @Singleton
    fun bindStreetsRepository(impl: StreetsRepositoryImpl): StreetsRepository

    @Binds
    @Singleton
    fun bindGrowthRepository(impl: GrowthRepositoryImpl): GrowthRepository

    @Binds
    @Singleton
    fun bindMapsRepository(impl: MapsRepositoryImpl): MapsRepository

    @Binds
    @Singleton
    fun bindAnimalsRepository(impl: AnimalsRepositoryImpl): AnimalsRepository

    @Binds
    @Singleton
    fun bindWidgetRepository(impl: WidgetRepositoryImpl): WidgetRepository
}