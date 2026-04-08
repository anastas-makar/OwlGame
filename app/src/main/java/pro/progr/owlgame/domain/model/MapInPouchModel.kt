package pro.progr.owlgame.domain.model

import pro.progr.owlgame.data.db.model.MapType
import pro.progr.owlgame.data.web.inpouch.ExpeditionInPouch

data class MapInPouchModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val type: MapType,
    val expedition: ExpeditionInPouch? = null
)