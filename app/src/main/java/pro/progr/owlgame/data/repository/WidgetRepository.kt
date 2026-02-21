package pro.progr.owlgame.data.repository

import android.net.Uri
import pro.progr.owlgame.data.db.Animal
import pro.progr.owlgame.data.db.MapEntity

interface WidgetRepository {
    fun getRandomMap(): MapEntity?

    fun getAnimal(): Animal?

    fun isPouchAvailable(): Boolean

    fun getUri(res : Int) : Uri
    fun clearAnimalDayAndId()

    fun getUri(path : String) : Uri
}