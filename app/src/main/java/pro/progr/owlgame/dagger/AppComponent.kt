package pro.progr.owlgame.dagger

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import pro.progr.owlgame.data.dagger.DatabaseModule
import pro.progr.owlgame.data.dagger.NetworkModule
import pro.progr.owlgame.presentation.viewmodel.dagger.MapViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.PouchViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.TownViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.ViewModelFactory
import pro.progr.owlgame.worker.GameWorkerFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [TownModule::class,
    PouchModule::class,
    NetworkModule::class,
    AppModule::class,
    DatabaseModule::class,
    WorkerModule::class
])
interface AppComponent {
    fun townViewModelFactory(): TownViewModelFactory

    fun pouchViewModelFactory(): PouchViewModelFactory

    fun mapViewModelFactory(): MapViewModelFactory

    fun viewModelFactory() : ViewModelFactory

    fun gameWorkerFactory() : GameWorkerFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }
}