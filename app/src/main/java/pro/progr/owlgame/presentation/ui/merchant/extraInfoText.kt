package pro.progr.owlgame.presentation.ui.merchant

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantItemExtraInfo

@Composable
fun extraInfoText(extra: MerchantItemExtraInfo): String {
    return when (extra) {
        is MerchantItemExtraInfo.BuildingCost ->
            stringResource(R.string.merchant_shop_building_cost, extra.amount)

        is MerchantItemExtraInfo.FurnitureInstallCost ->
            stringResource(R.string.merchant_shop_furniture_install_cost, extra.amount)

        is MerchantItemExtraInfo.SeedAmount ->
            stringResource(R.string.merchant_shop_seed_amount, extra.amount)

        MerchantItemExtraInfo.RecipeUnlock ->
            stringResource(R.string.merchant_shop_recipe_unlock)
    }
}