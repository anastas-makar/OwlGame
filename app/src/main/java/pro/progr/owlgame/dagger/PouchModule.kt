package pro.progr.owlgame.dagger

import dagger.Module
import dagger.Provides
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.domain.SaveMapsUseCase
import pro.progr.owlgame.presentation.viewmodel.dagger.PouchViewModelFactory

@Module
class PouchModule {

    @Provides
    fun providePouchViewModelFactory(
        pouchesRepository: PouchesRepository,
        saveMapsUseCase: SaveMapsUseCase
    ): PouchViewModelFactory {
        return PouchViewModelFactory(pouchesRepository, saveMapsUseCase)
    }
}
