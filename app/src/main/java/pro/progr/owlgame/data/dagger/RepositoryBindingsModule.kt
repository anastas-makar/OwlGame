package pro.progr.owlgame.data.dagger

import dagger.Binds
import dagger.Module
import pro.progr.owlgame.domain.repository.AnimalsRepository
import pro.progr.owlgame.domain.repository.BuildingsRepository
import pro.progr.owlgame.domain.repository.ExpeditionsRepository
import pro.progr.owlgame.domain.repository.FurnitureRepository
import pro.progr.owlgame.domain.repository.GardenItemsRepository
import pro.progr.owlgame.domain.repository.GrowthRepository
import pro.progr.owlgame.domain.repository.ImageRepository
import pro.progr.owlgame.domain.repository.MapsRepository
import pro.progr.owlgame.domain.repository.PlantsRepository
import pro.progr.owlgame.domain.repository.PouchesRepository
import pro.progr.owlgame.domain.repository.SlotsRepository
import pro.progr.owlgame.domain.repository.StreetsRepository
import pro.progr.owlgame.domain.repository.SuppliesRepository
import pro.progr.owlgame.domain.repository.SupplyToRecipeRepository
import pro.progr.owlgame.domain.repository.WidgetRepository
import pro.progr.owlgame.data.repository.impl.AnimalsRepositoryImpl
import pro.progr.owlgame.data.repository.impl.BuildingsRepositoryImpl
import pro.progr.owlgame.data.repository.impl.ExpeditionsRepositoryImpl
import pro.progr.owlgame.data.repository.impl.FurnitureRepositoryImpl
import pro.progr.owlgame.data.repository.impl.GardenItemsRepositoryImpl
import pro.progr.owlgame.data.repository.impl.GrowthRepositoryImpl
import pro.progr.owlgame.data.repository.impl.ImageRepositoryImpl
import pro.progr.owlgame.data.repository.impl.MapsRepositoryImpl
import pro.progr.owlgame.data.repository.impl.PlantsRepositoryImpl
import pro.progr.owlgame.data.repository.impl.PouchesRepositoryImpl
import pro.progr.owlgame.data.repository.impl.SlotsRepositoryImpl
import pro.progr.owlgame.data.repository.impl.StreetsRepositoryImpl
import pro.progr.owlgame.data.repository.impl.SuppliesRepositoryImpl
import pro.progr.owlgame.data.repository.impl.SupplyToRecipeRepositoryImpl
import pro.progr.owlgame.data.repository.impl.WidgetRepositoryImpl
import javax.inject.Singleton

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
    fun bindExpeditionsRepository(impl: ExpeditionsRepositoryImpl): ExpeditionsRepository

    @Binds
    @Singleton
    fun bindAnimalsRepository(impl: AnimalsRepositoryImpl): AnimalsRepository

    @Binds
    @Singleton
    fun bindWidgetRepository(impl: WidgetRepositoryImpl): WidgetRepository
}