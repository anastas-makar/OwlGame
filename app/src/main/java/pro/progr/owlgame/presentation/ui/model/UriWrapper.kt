package pro.progr.owlgame.presentation.ui.model

import android.content.Context
import android.net.Uri
import java.io.File

class UriWrapper(val uri: Uri) {
    constructor(resource: Int, context: Context)
            : this(Uri.parse(
        "android.resource://${context.packageName}/drawable/" +
                context.resources.getResourceEntryName(resource)))

    constructor(resource: String) : this(Uri.fromFile(File(resource)))
}