package pro.progr.owlgame.dagger

import dagger.Component
import pro.progr.owlgame.presentation.viewmodel.dagger.TownViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.ViewModelFactory

@Component(modules = [TownModule::class])
interface AppComponent {
    fun townViewModelFactory(): TownViewModelFactory

    fun viewModelFactory() : ViewModelFactory
}