package pro.progr.owlgame.dagger

import dagger.Component
import pro.progr.owlgame.presentation.viewmodel.dagger.TownViewModelFactory

@Component(modules = [TownModule::class])
interface AppComponent {
    fun viewModelFactory(): TownViewModelFactory
}