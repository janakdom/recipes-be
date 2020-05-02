package cz.st52530.recipes.service

import cz.st52530.recipes.dao.RecipeRepository
import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.model.database.User
import org.springframework.stereotype.Service

@Service
class RecipeService(
        private val recipeRepository: RecipeRepository
) : IRecipeService {

    override fun getByUser(user: User): List<Recipe> {
        return recipeRepository.findByAuthorOrderByCreatedAt(user)
    }

    override fun getById(id: Int): Recipe {
        val recipe = recipeRepository.findById(id).orElseThrow()
        return recipe
    }
}