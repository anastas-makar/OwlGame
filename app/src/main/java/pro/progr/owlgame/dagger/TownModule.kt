package pro.progr.owlgame.dagger

import dagger.Module
import dagger.Provides
import pro.progr.owlgame.data.repository.TownRepository
import pro.progr.owlgame.presentation.viewmodel.dagger.TownViewModelFactory

@Module
class TownModule {

    @Provides
    fun provideTownViewModelFactory(
        townRepository: TownRepository
    ): TownViewModelFactory {
        return TownViewModelFactory(townRepository)
    }
}
