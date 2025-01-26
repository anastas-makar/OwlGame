package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.data.web.Map
import pro.progr.owlgame.data.web.inpouch.DiamondsInPouchModel
import pro.progr.owlgame.data.web.inpouch.InPouch
import javax.inject.Inject

import pro.progr.owlgame.data.web.inpouch.MapInPouchModel
import pro.progr.owlgame.domain.SaveMapsUseCase

class InPouchViewModel @Inject constructor(
    private val pouchesRepository: PouchesRepository,
    private val saveMapsUseCase: SaveMapsUseCase
) : ViewModel() {
    val inPouch = mutableStateOf<InPouch?>(null)
    fun loadInPouch(pouchId : String) {
        //todo:
        val inPouchTemp = InPouch(maps = listOf(
            MapInPouchModel(
            id = "",
            name = "Карта болотистой местности",
            imageUrl = "https://progr.pro//api//owl//maps//img//f250800d-6224-4e1c-a6ea-472c1796bb8b.webp"
        )
        ), diamonds = DiamondsInPouchModel(25)
        )

        saveMaps(inPouchTemp.maps)

        inPouch.value = inPouchTemp
    }

    private fun saveMaps(maps : List<MapInPouchModel>) {
        saveMapsUseCase.invoke(maps.map {
            Map(
                it.id,
                it.name,
                it.imageUrl
            )
        })
    }
}