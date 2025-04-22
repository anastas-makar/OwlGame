package pro.progr.owlgame.presentation.viewmodel.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pro.progr.owlgame.data.repository.AnimalsRepository
import pro.progr.owlgame.presentation.viewmodel.AnimalViewModel
import javax.inject.Inject

class AnimalViewModelFactory @Inject constructor(
    val animalsRepository: AnimalsRepository
) : ViewModelProvider.Factory {
    var animalId : String = ""

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimalViewModel::class.java)) {
            return AnimalViewModel(animalsRepository, animalId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}