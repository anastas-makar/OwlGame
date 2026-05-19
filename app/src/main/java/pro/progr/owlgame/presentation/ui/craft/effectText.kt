package pro.progr.owlgame.presentation.ui.craft

import pro.progr.owlgame.domain.model.EffectType
import pro.progr.owlgame.domain.model.RecipeModel
import pro.progr.owlgame.domain.model.SupplyModel

fun SupplyModel.effectText(): String? {
    return when (effectType) {
        EffectType.HEAL -> "Защита: +$effectAmount"
        EffectType.DAMAGE -> "Атака: +$effectAmount"
        EffectType.NO_EFFECT -> null
    }
}

fun RecipeModel.effectText(): String? {
    return when (effectType) {
        EffectType.HEAL -> "Защита: +$effectAmount"
        EffectType.DAMAGE -> "Атака: +$effectAmount"
        EffectType.NO_EFFECT -> null
    }
}