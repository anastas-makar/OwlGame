package pro.progr.owlgame.presentation.ui.model

import android.net.Uri

data class OwlMenuModel(
    val text : String,
    val navigateTo : String,
    val imageUri : Uri
)