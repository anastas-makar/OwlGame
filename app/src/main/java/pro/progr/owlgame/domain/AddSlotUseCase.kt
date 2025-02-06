package pro.progr.owlgame.domain

import pro.progr.owlgame.data.repository.TownRepository
import javax.inject.Inject

class AddSlotUseCase @Inject constructor(val townRepository: TownRepository) {
/*    operator fun invoke(town: Town, slotNum :Int) : Flow<MapData> {

        return townRepository.insertSlot(town, slotNum = slotNum, buildingId = 0)
    }*/
}