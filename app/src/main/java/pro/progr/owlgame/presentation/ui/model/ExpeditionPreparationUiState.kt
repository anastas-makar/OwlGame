package pro.progr.owlgame.presentation.ui.model

import pro.progr.owlgame.data.db.EffectType

data class ExpeditionPreparationUiState(
    val items: List<SupplySelectionUi> = emptyList(),
    val extraHealText: String = "",
    val extraDamageText: String = "",
    val isStarting: Boolean = false,
    val errorMessage: String? = null
) {
    val extraHeal: Int
        get() = extraHealText.toIntOrNull()?.coerceAtLeast(0) ?: 0

    val extraDamage: Int
        get() = extraDamageText.toIntOrNull()?.coerceAtLeast(0) ?: 0

    val totalHeal: Int
        get() = items.sumOf {
            if (it.supply.effectType == EffectType.HEAL) {
                it.selectedAmount * it.supply.effectAmount
            } else 0
        } + extraHeal

    val totalDamage: Int
        get() = items.sumOf {
            if (it.supply.effectType == EffectType.DAMAGE) {
                it.selectedAmount * it.supply.effectAmount
            } else 0
        } + extraDamage

    val diamondsCost: Int
        get() = extraHeal + extraDamage
}
