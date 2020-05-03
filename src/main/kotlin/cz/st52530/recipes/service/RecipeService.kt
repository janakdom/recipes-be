package cz.st52530.recipes.service

import cz.st52530.recipes.dao.CategoryRepository
import cz.st52530.recipes.dao.RecipeRepository
import cz.st52530.recipes.extensions.ensureNotBlank
import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.model.database.User
import cz.st52530.recipes.model.dto.RecipeDto
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import java.lang.IllegalStateException
import java.util.*

@Service
class RecipeService(
        private val recipeRepository: RecipeRepository,
        private val categoryRepository: CategoryRepository
) : IRecipeService {

    override fun getByUser(user: User): List<Recipe> {
        return recipeRepository.findByAuthorOrderByCreatedAt(user)
    }

    override fun getById(id: Int): Recipe {
        val recipe = recipeRepository.findById(id).orElseThrow()
        return recipe
    }

    override fun addRecipe(data: RecipeDto, author: User): Recipe {
        val categories = categoryRepository.findAllByIdIn(data.categories).toSet()
        if (categories.isEmpty()) {
            throw IllegalArgumentException("Category cannot be empty!")
        }

        val recipeData = Recipe(
                name = data.name.ensureNotBlank(),
                author = author,
                categories = categories,
                createdAt = Date(),
                description = data.description.ensureNotBlank(),
                instructions = data.instructions.ensureNotBlank(),
                preparationTime = data.preparationTime.ensureNotBlank()
        )

        return recipeRepository.save(recipeData)
    }

    override fun updateRecipe(recipeId: Int, data: RecipeDto, author: User): Recipe {
        val recipe = recipeRepository.findById(recipeId).orElseThrow()
        // Only author can update the recipe.
        if (recipe.author.id != author.id) {
            throw AccessDeniedException("Not allowed to change this recipe!")
        }

        // Remove not used categories.
        val updatedCategories = recipe.categories.filter { data.categories.contains(it.id) }.toMutableSet()

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
}