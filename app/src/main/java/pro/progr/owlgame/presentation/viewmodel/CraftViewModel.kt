package pro.progr.owlgame.presentation.viewmodel

import androidx.lifecycle.ViewModel
import pro.progr.owlgame.domain.ObserveRecipesUseCase
import javax.inject.Inject

class CraftViewModel@Inject constructor(
    private val observeRecipesUseCase: ObserveRecipesUseCase
) : ViewModel() {
}