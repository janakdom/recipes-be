package cz.st52530.recipes.model.dto

data class UpdateRecipeDto(
        val name: String,
        val description: String,
        val preparationTime: String,
        val instructions: String,
        val categories: List<Int>,
        val ingredients: List<UpdateRecipeIngredientDto>
)