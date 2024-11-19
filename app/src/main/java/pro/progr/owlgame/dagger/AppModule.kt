package pro.progr.owlgame.dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule(private val application:Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application.applicationContext
}
