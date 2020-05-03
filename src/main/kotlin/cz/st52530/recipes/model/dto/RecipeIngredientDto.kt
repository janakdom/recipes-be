package cz.st52530.recipes.model.dto

import cz.st52530.recipes.model.database.Ingredient

data class RecipeIngredientDto(
        val ingredient: Ingredient,
        val amount: String
)