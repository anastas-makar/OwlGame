package pro.progr.owlgame.dagger

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import pro.progr.owlgame.data.dagger.DatabaseModule
import pro.progr.owlgame.data.dagger.NetworkModule
import pro.progr.owlgame.presentation.viewmodel.dagger.AnimalViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.MapViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.PouchViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.ViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [PouchModule::class,
    NetworkModule::class,
    AppModule::class,
    DatabaseModule::class
])
interface AppComponent {

    fun pouchViewModelFactory(): PouchViewModelFactory

    fun mapViewModelFactory(): MapViewModelFactory

    fun animalViewModelFactory(): AnimalViewModelFactory

    fun viewModelFactory() : ViewModelFactory



    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }
}