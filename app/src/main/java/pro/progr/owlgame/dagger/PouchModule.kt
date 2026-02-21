package pro.progr.owlgame.dagger

import dagger.Module
import dagger.Provides
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.domain.SaveBuildingsUseCase
import pro.progr.owlgame.domain.SaveFurnitureUseCase
import pro.progr.owlgame.domain.SaveGardenItemsUseCase
import pro.progr.owlgame.domain.SaveMapsUseCase
import pro.progr.owlgame.domain.SavePlantsUseCase
import pro.progr.owlgame.domain.SaveRecipesUseCase
import pro.progr.owlgame.presentation.viewmodel.dagger.PouchViewModelFactory

@Module
class PouchModule {

    @Provides
    fun providePouchViewModelFactory(
        pouchesRepository: PouchesRepository,
        saveBuildingsUseCase: SaveBuildingsUseCase,
        saveMapsUseCase: SaveMapsUseCase,
        savePlantsUseCase: SavePlantsUseCase,
        saveGardenItemsUseCase: SaveGardenItemsUseCase,
        saveFurnitureUseCase: SaveFurnitureUseCase,
        saveRecipesUseCase: SaveRecipesUseCase
    ): PouchViewModelFactory {
        return PouchViewModelFactory(pouchesRepository,
            saveMapsUseCase,
            saveBuildingsUseCase,
            savePlantsUseCase,
            saveGardenItemsUseCase,
            saveFurnitureUseCase,
            saveRecipesUseCase)
    }
}
