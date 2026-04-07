package pro.progr.owlgame.data.web.inpouch

import pro.progr.owlgame.data.db.model.MapType

data class MapInPouchModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val type: MapType,
    val expedition: ExpeditionInPouch? = null
)