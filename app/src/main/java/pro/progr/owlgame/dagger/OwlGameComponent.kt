package pro.progr.owlgame.dagger

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import pro.progr.authapi.AuthInterface
import pro.progr.owlgame.data.dagger.DatabaseModule
import pro.progr.owlgame.data.dagger.NetworkModule
import pro.progr.owlgame.presentation.viewmodel.dagger.AnimalViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.BuildingViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.MapViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.PouchViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.MapsViewModelFactory
import pro.progr.owlgame.presentation.viewmodel.dagger.WidgetViewModelFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [PouchModule::class,
    NetworkModule::class,
    OwlGameModule::class,
    DatabaseModule::class
])
interface OwlGameComponent {

    fun pouchViewModelFactory(): PouchViewModelFactory

    fun mapViewModelFactory(): MapViewModelFactory

    fun buildingViewModelFactory(): BuildingViewModelFactory

    fun animalViewModelFactory(): AnimalViewModelFactory

    fun widgetViewModelFactory(): WidgetViewModelFactory

    fun mapsViewModelFactory() : MapsViewModelFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun auth(auth: AuthInterface): Builder

        fun appModule(owlGameModule: OwlGameModule): Builder

        fun build(): OwlGameComponent
    }
}