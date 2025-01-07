package pro.progr.owlgame.data.web.inpouch

data class InPouch(
    val buildings : List<BuildingInPouch> = emptyList(),
    val maps : List<MapInPouchModel> = emptyList(),
    val diamonds : List<DiamondsInPouchModel> = emptyList()
)