package cz.st52530.recipes.service

import cz.st52530.recipes.dao.CategoryRepository
import cz.st52530.recipes.dao.RecipeRepository
import cz.st52530.recipes.extensions.ensureNotBlank
import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.model.database.User
import cz.st52530.recipes.model.dto.RecipeDto
import org.springframework.stereotype.Service
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

    override fun addRecipe(data: RecipeDto, author: User) {
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
        recipeRepository.save(recipeData)
    }
}