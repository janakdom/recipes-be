package cz.st52530.recipes.model.dto

import cz.st52530.recipes.model.database.Instruction

data class UpdateRecipeDto(
        val name: String,
        val description: String,
        val preparationTime: String,
        val instructions: List<Instruction>,
        val categories: List<Int>,
        val ingredients: List<UpdateRecipeIngredientDto>
)