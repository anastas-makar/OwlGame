package pro.progr.owlgame.domain.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.MapModel

interface WidgetRepository {
    fun getRandomMap(): MapModel?

    fun observeSearchingAnimal(): Flow<AnimalModel?>

    fun observeInitialRestoreCompleted(): Flow<Boolean>

    fun isPouchAvailable(): Boolean

    fun getUri(res : Int) : Uri
    fun getUri(path : String) : Uri

    fun isMerchantAvailable() : Boolean

    fun markMerchantOpened()
}
