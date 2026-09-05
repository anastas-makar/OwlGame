package pro.progr.owlgame.presentation.ui.model

data class WidgetMenuUiState(
    val isLoading: Boolean = true,
    val menuItems: List<OwlMenuModel> = emptyList()
)
