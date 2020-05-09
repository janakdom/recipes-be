package cz.st52530.recipes.model.dto

data class UpdateRecipeIngredientDto(
        val ingredientId: Int,
        val amount: String
) {

    // Empty constructor for custom deserialization.
    constructor() : this(
            ingredientId = 0,
            amount = ""
    )
}