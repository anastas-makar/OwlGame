package pro.progr.owlgame.dagger

import dagger.Component
import pro.progr.owlgame.data.dagger.DatabaseModule
import pro.progr.owlgame.data.dagger.NetworkModule
import pro.progr.owlgame.presentation.viewmodel.dagger.MapViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.TownViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.ViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [TownModule::class,
    NetworkModule::class,
    AppModule::class,
    DatabaseModule::class
])
interface AppComponent {
    fun townViewModelFactory(): TownViewModelFactory

    fun mapViewModelFactory(): MapViewModelFactory

    fun viewModelFactory() : ViewModelFactory
}