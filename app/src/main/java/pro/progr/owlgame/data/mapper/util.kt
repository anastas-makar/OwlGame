package pro.progr.owlgame.data.mapper

fun linkId(recipeId: String, supplyId: String): String =
    "${recipeId}__${supplyId}" // стабильный id для SupplyToRecipe
