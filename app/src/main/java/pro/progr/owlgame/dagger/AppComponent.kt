package pro.progr.owlgame.dagger

import dagger.Component
import pro.progr.owlgame.data.dagger.NetworkModule
import pro.progr.owlgame.presentation.viewmodel.dagger.TownViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.ViewModelFactory
import javax.inject.Singleton

@Component(modules = [TownModule::class, NetworkModule::class])
interface AppComponent {
    fun townViewModelFactory(): TownViewModelFactory

    fun viewModelFactory() : ViewModelFactory
}