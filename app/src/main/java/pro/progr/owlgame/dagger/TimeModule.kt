package pro.progr.owlgame.dagger

import dagger.Module
import dagger.Provides
import java.time.Clock
import javax.inject.Singleton

@Module
object TimeModule {

    @Provides
    @Singleton
    fun provideClock(): Clock = Clock.systemDefaultZone()
}