package pro.progr.owlgame.domain.repository

import android.net.Uri
import pro.progr.owlgame.domain.model.AnimalModel
import pro.progr.owlgame.domain.model.MapModel

interface WidgetRepository {
    fun getRandomMap(): MapModel?

    fun getAnimal(): AnimalModel?

    fun isPouchAvailable(): Boolean

    fun getUri(res : Int) : Uri
    fun clearAnimalDayAndId()

    fun getUri(path : String) : Uri
}