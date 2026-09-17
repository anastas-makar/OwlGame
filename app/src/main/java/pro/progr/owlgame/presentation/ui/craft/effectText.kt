package pro.progr.owlgame.presentation.ui.craft

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pro.progr.owlgame.R
import pro.progr.owlgame.domain.model.EffectType
import pro.progr.owlgame.domain.model.RecipeModel
import pro.progr.owlgame.domain.model.SupplyModel

@Composable
fun SupplyModel.effectText(): String? {
    return when (effectType) {
        EffectType.HEAL -> stringResource(R.string.defense_effect, effectAmount)
        EffectType.DAMAGE -> stringResource(R.string.attack_effect, effectAmount)
        EffectType.NO_EFFECT -> null
    }
}

@Composable
fun RecipeModel.effectText(): String? {
    return when (effectType) {
        EffectType.HEAL -> stringResource(R.string.defense_effect, effectAmount)
        EffectType.DAMAGE -> stringResource(R.string.attack_effect, effectAmount)
        EffectType.NO_EFFECT -> null
    }
}
