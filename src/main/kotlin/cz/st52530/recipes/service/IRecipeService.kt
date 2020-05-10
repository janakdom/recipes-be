package cz.st52530.recipes.service

import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.model.database.User
import cz.st52530.recipes.model.dto.RecipeDto
import cz.st52530.recipes.model.dto.UpdateRecipeDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IRecipeService {

    fun getByUser(user: User, pageable: Pageable): Page<Recipe>

    fun getById(id: Int, currentUser: User): RecipeDto

    fun addRecipe(data: UpdateRecipeDto, currentUser: User): RecipeDto

    fun updateRecipeImage(recipe: RecipeDto, imageUrl: String, currentUser: User): RecipeDto

    fun updateRecipe(recipeId: Int, data: UpdateRecipeDto, newImageUrl: String?, currentUser: User): RecipeDto

    fun deleteRecipe(recipeId: Int, currentUser: User)
}