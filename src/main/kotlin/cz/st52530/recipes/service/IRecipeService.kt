package cz.st52530.recipes.service

import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.model.database.User
import cz.st52530.recipes.model.dto.RecipeDto

interface IRecipeService {

    fun getByUser(user: User): List<Recipe>

    fun getById(id: Int, currentUser: User): Recipe

    fun addRecipe(data: RecipeDto, currentUser: User): Recipe

    fun updateRecipe(recipeId: Int, data: RecipeDto, currentUser: User): Recipe

    fun deleteRecipe(recipeId: Int, currentUser: User)
}