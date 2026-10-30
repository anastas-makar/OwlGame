package pro.progr.owlgame.dagger

import android.app.Application
import android.content.Context
import pro.progr.diagnostics.Diagnostics
import pro.progr.diagnostics.NoOpDiagnostics

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class OwlGameModule(
    private val application: Application,
    private val diagnostics: Diagnostics = NoOpDiagnostics
) {

    @Provides
    @Singleton
    fun provideContext(): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideDiagnostics(): Diagnostics = diagnostics
}
