package cz.st52530.recipes.service

import cz.st52530.recipes.dao.CategoryRepository
import cz.st52530.recipes.dao.IngredientRepository
import cz.st52530.recipes.dao.RecipeIngredientRepository
import cz.st52530.recipes.dao.RecipeRepository
import cz.st52530.recipes.extensions.ensureNotBlank
import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.model.database.RecipeIngredient
import cz.st52530.recipes.model.database.User
import cz.st52530.recipes.model.database.id.RecipeIngredientIdentity
import cz.st52530.recipes.model.dto.RecipeDto
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import java.lang.IllegalStateException
import java.util.*

@Service
class RecipeService(
        private val recipeRepository: RecipeRepository,
        private val categoryRepository: CategoryRepository,
        private val ingredientRepository: IngredientRepository,
        private val recipeIngredientRepository: RecipeIngredientRepository
) : IRecipeService {

    override fun getByUser(user: User): List<Recipe> {
        return recipeRepository.findByAuthorOrderByCreatedAt(user)
    }

    override fun getById(id: Int, currentUser: User): Recipe {
        val recipe = recipeRepository.findById(id).orElseThrow()
        // Only author can see the recipe.
        if (recipe.author.id != currentUser.id) {
            throw AccessDeniedException("Not allowed to see this recipe!")
        }
        return recipe
    }

    override fun addRecipe(data: RecipeDto, currentUser: User): Recipe {
        val categories = categoryRepository.findAllByIdIn(data.categories)
        if (categories.size != data.categories.size) {
            throw IllegalArgumentException("Category was invalid!")
        }
        if (categories.isEmpty()) {
            throw IllegalArgumentException("Category cannot be empty!")
        }

        val ingredientIds = data.ingredients.map { it.ingredientId }
        val ingredients = ingredientRepository.findAllByIdIn(ingredientIds)
        if (ingredients.size != data.ingredients.size) {
            throw IllegalArgumentException("Ingredient was invalid!")
        }
        if (ingredients.isEmpty()) {
            throw IllegalArgumentException("Ingredient cannot be empty!")
        }

        val recipeData = Recipe(
                name = data.name.ensureNotBlank(),
                author = currentUser,
                categories = categories,
                createdAt = Date(),
                description = data.description.ensureNotBlank(),
                instructions = data.instructions.ensureNotBlank(),
                preparationTime = data.preparationTime.ensureNotBlank()
        )
        val cretedRecipe = recipeRepository.save(recipeData)

        val recipeIngredients = data.ingredients.map { ingredientDto ->
            val identity = RecipeIngredientIdentity(
                    recipeId = cretedRecipe.id,
                    ingredientId = ingredientDto.ingredientId
            )
            RecipeIngredient(identity, ingredientDto.amount)
        }
        recipeIngredientRepository.saveAll(recipeIngredients)

        return cretedRecipe
    }

    override fun updateRecipe(recipeId: Int, data: RecipeDto, currentUser: User): Recipe {
        val recipe = recipeRepository.findById(recipeId).orElseThrow()
        // Only author can update the recipe.
        if (recipe.author.id != currentUser.id) {
            throw AccessDeniedException("Not allowed to change this recipe!")
        }

        // Remove not used categories.
        val updatedCategories = recipe.categories.filter { data.categories.contains(it.id) }.toMutableList()

        // Create a list of new categories.
        val newCategories = data.categories.filter { newCategoryId ->
            updatedCategories.none { it.id == newCategoryId }
        }

        // Add new categories to the recipe.
        if (newCategories.isNotEmpty()) {
            val categories = categoryRepository.findAllByIdIn(newCategories)
            if (categories.isEmpty()) {
                throw IllegalArgumentException("Invalid category used!")
            }
            updatedCategories.addAll(categories)
        }

        // Category list cannot become empty!
        if (updatedCategories.isEmpty()) {
            throw IllegalStateException("Categories cannot become empty!")
        }

        recipe.run {
            name = data.name.ensureNotBlank()
            description = data.description.ensureNotBlank()
            instructions = data.instructions.ensureNotBlank()
            preparationTime = data.preparationTime.ensureNotBlank()
            categories = updatedCategories
        }

        return recipeRepository.save(recipe)
    }

    override fun deleteRecipe(recipeId: Int, currentUser: User) {
        val recipe = recipeRepository.findById(recipeId).orElseThrow()
        // Only author can delete the recipe.
        if (recipe.author.id != currentUser.id) {
            throw AccessDeniedException("Not allowed to delete this recipe!")
        }
        recipeRepository.delete(recipe)
    }
}