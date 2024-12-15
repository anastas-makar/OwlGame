package pro.progr.owlgame.domain

import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.data.db.Town
import pro.progr.owlgame.data.db.TownWithData
import pro.progr.owlgame.data.repository.TownRepository
import javax.inject.Inject

class AddSlotUseCase @Inject constructor(val townRepository: TownRepository) {
    operator fun invoke(town: Town, slotNum :Int) : Flow<TownWithData> {

        return townRepository.insertSlot(town, slotNum = slotNum, buildingId = 0)
    }
}