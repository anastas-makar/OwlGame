package pro.progr.owlgame.domain

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
    suspend operator fun invoke() {
        val growthState = growthRepository.getGrowthState()

        if (growthState is GrowthState.Growing) {
            plantsRepository.addReadinessToAllPlanted(growthState.delta)
            gardenItemsRepository.addReadinessToAllPlanted(growthState.delta)
            growthRepository.setGrowthUpdate(System.currentTimeMillis())
        }
    }
}