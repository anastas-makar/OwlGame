package pro.progr.owlgame.domain

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import pro.progr.owlgame.data.repository.GardenItemsRepository
import pro.progr.owlgame.data.repository.GrowthRepository
import pro.progr.owlgame.data.repository.GrowthState
import pro.progr.owlgame.data.repository.PlantsRepository
import javax.inject.Inject

class ApplyOfflineGrowthUseCase @Inject constructor(
    private val plantsRepository: PlantsRepository,
    private val gardenItemsRepository: GardenItemsRepository,
    private val growthRepository: GrowthRepository
) {
    private val mutex = Mutex()

    suspend operator fun invoke() = mutex.withLock {
        when (val growthState = growthRepository.getGrowthState()) {
            is GrowthState.Growing -> {
                plantsRepository.addReadinessToAllPlanted(growthState.delta)
                gardenItemsRepository.addReadinessToAllPlanted(growthState.delta)
                growthRepository.setGrowthUpdate(growthState.updateTime)
            }

            is GrowthState.NeedsStart -> growthRepository.setGrowthUpdate(growthState.updateTime)
            is GrowthState.Suspended -> Unit
        }
    }

}