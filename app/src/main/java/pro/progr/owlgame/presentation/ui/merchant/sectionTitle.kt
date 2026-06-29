package pro.progr.owlgame.presentation.ui.merchant

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import pro.progr.owlgame.R
import pro.progr.owlgame.presentation.ui.model.merchant.MerchantShopSectionType

@Composable
fun sectionTitle(type: MerchantShopSectionType): String {
    return when (type) {
        MerchantShopSectionType.BUILDINGS ->
            stringResource(R.string.merchant_shop_section_buildings)

        MerchantShopSectionType.MAPS ->
            stringResource(R.string.merchant_shop_section_maps)

        MerchantShopSectionType.LOCATIONS ->
            stringResource(R.string.merchant_shop_section_locations)

        MerchantShopSectionType.FURNITURE ->
            stringResource(R.string.merchant_shop_section_furniture)

        MerchantShopSectionType.PLANTS ->
            stringResource(R.string.merchant_shop_section_plants)

        MerchantShopSectionType.GARDEN_ITEMS ->
            stringResource(R.string.merchant_shop_section_garden_items)

        MerchantShopSectionType.RECIPES ->
            stringResource(R.string.merchant_shop_section_recipes)
    }
}