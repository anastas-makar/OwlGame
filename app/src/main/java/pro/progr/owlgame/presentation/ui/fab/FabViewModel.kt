package pro.progr.owlgame.presentation.ui.fab

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class FabViewModel  : ViewModel() {
    val showFab = mutableStateOf(true)
    val fabExpanded = mutableStateOf(false)
}