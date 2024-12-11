package pro.progr.owlgame.domain

import pro.progr.owlgame.data.db.Town
import pro.progr.owlgame.data.repository.TownRepository
import javax.inject.Inject

class AddSlotUseCase @Inject constructor(val townRepository: TownRepository) {
    operator fun invoke(town: Town, slotNum :Int) : Town {

        return townRepository.insertSlot(town.id, slotNum)
    }
}