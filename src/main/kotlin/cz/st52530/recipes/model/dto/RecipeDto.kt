package cz.st52530.recipes.model.dto

import cz.st52530.recipes.model.database.Category
import cz.st52530.recipes.model.database.Instruction
import cz.st52530.recipes.model.database.User

data class RecipeDto(
        val id: Int,
        val name: String,
        val description: String,
        val preparationTime: String,
        val instructions: List<Instruction>,
        val author: User,
        val categories: List<Category>,
        val ingredients: List<RecipeIngredientDto>
)