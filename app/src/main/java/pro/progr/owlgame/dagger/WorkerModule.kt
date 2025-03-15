package pro.progr.owlgame.dagger

import androidx.work.WorkerFactory
import dagger.Binds
import dagger.Module
import pro.progr.owlgame.worker.GameWorkerFactory

@Module
interface WorkerModule {
    @Binds
    fun bindGameWorkerFactory(factory: GameWorkerFactory): WorkerFactory
}