package cz.st52530.recipes.service

import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.model.database.User
import cz.st52530.recipes.model.dto.RecipeDto
import cz.st52530.recipes.model.dto.UpdateRecipeDto

interface IRecipeService {

    fun getByUser(user: User): List<Recipe>

    fun getById(id: Int, currentUser: User): RecipeDto

    fun addRecipe(data: UpdateRecipeDto, currentUser: User): RecipeDto

    fun updateRecipe(recipeId: Int, data: UpdateRecipeDto, currentUser: User): RecipeDto

    fun deleteRecipe(recipeId: Int, currentUser: User)
}