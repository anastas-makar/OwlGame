package pro.progr.owlgame.domain.model

import java.util.Locale

enum class GameLocale(val apiValue: String) {
    EN("en"),
    RU("ru");

    companion object {
        fun fromLanguageCode(languageCode: String?): GameLocale {
            val normalized = languageCode
                ?.trim()
                ?.lowercase(Locale.ROOT)

            return entries.firstOrNull { it.apiValue == normalized } ?: EN
        }
    }
}
