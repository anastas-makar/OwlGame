package pro.progr.owlgame.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import pro.progr.owlgame.data.repository.PouchesRepository
import pro.progr.owlgame.data.web.inpouch.InPouch
import javax.inject.Inject

import pro.progr.owlgame.data.web.inpouch.MapInPouchModel

class InPouchViewModel @Inject constructor(
    private val pouchesRepository: PouchesRepository
) : ViewModel() {
    val inPouch = mutableStateOf<InPouch?>(null)
    fun loadInPouch(pouchId : String) {
        //todo:
        inPouch.value = InPouch(maps = listOf(
            MapInPouchModel(
            id = "",
            name = "",
            imageUrl = "https://progr.pro//api//owl//maps//img//f250800d-6224-4e1c-a6ea-472c1796bb8b.webp"
        )
        ))
    }
}