package cz.st52530.recipes.model.dto

data class RecipeDto(
        val name: String,
        val description: String,
        val preparationTime: String,
        val instructions: String,
        val categories: Set<Int>
)