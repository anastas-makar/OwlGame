package pro.progr.owlgame.presentation.viewmodel.dagger

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pro.progr.owlgame.dagger.AppModule
import pro.progr.owlgame.dagger.DaggerAppComponent

@Composable
inline fun <reified VM : ViewModel> DaggerPouchViewModel() : VM {
    val application = LocalContext.current.applicationContext as Application

    val factory : PouchViewModelFactory = DaggerAppComponent.builder()
        .application(application)
        .appModule(AppModule(application))
        .build()
        .pouchViewModelFactory()

    return viewModel(factory = factory)
}