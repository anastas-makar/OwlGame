package pro.progr.owlgame.dagger

import dagger.Module
import dagger.Provides
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.domain.SaveMapUseCase
import pro.progr.owlgame.presentation.viewmodel.dagger.PouchViewModelFactory

@Module
class PouchModule {

    @Provides
    fun providePouchViewModelFactory(
        pouchesRepository: PouchesRepository,
        saveMapUseCase: SaveMapUseCase
    ): PouchViewModelFactory {
        return PouchViewModelFactory(pouchesRepository, saveMapUseCase)
    }
}
