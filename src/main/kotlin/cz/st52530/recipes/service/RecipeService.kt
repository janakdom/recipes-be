package cz.st52530.recipes.service

import cz.st52530.recipes.dao.CategoryRepository
import cz.st52530.recipes.dao.IngredientRepository
import cz.st52530.recipes.dao.RecipeIngredientRepository
import cz.st52530.recipes.dao.RecipeRepository
import cz.st52530.recipes.extensions.ensureNotBlank
import cz.st52530.recipes.model.database.*
import cz.st52530.recipes.model.database.id.RecipeIngredientIdentity
import cz.st52530.recipes.model.dto.RecipeDto
import cz.st52530.recipes.model.dto.RecipeIngredientDto
import cz.st52530.recipes.model.dto.UpdateRecipeDto
import cz.st52530.recipes.model.dto.UpdateRecipeIngredientDto
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

    override fun getById(id: Int, currentUser: User): RecipeDto {
        val recipe = recipeRepository.findById(id).orElseThrow()
        // Only author can see the recipe.
        if (recipe.author.id != currentUser.id) {
            throw AccessDeniedException("Not allowed to see this recipe!")
        }
        val recipeIngredients = recipeIngredientRepository.findAllByIdentityRecipeId(recipe.id)
        return recipe.toDto(
                recipeIngredients = recipeIngredients,
                ingredients = null
        )
    }

    override fun addRecipe(data: UpdateRecipeDto, currentUser: User): RecipeDto {
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

        return cretedRecipe.toDto(
                recipeIngredients = recipeIngredients,
                ingredients = ingredients
        )
    }

    override fun updateRecipe(recipeId: Int, data: UpdateRecipeDto, currentUser: User): RecipeDto {
        val recipe = recipeRepository.findById(recipeId).orElseThrow()
        // Only author can update the recipe.
        if (recipe.author.id != currentUser.id) {
            throw AccessDeniedException("Not allowed to change this recipe!")
        }

        val updatedCategories = getUpdatedCategoriesForRecipe(recipe, data.categories)

        recipe.run {
            name = data.name.ensureNotBlank()
            description = data.description.ensureNotBlank()
            instructions = data.instructions.ensureNotBlank()
            preparationTime = data.preparationTime.ensureNotBlank()
            categories = updatedCategories
        }

        val recipeIngredients = updateRecipeIngredients(recipe, data.ingredients)

        return recipeRepository.save(recipe).toDto(
                recipeIngredients = recipeIngredients,
                ingredients = null
        )
    }

    /**
     * Validates [allCategoryIds] and returns list of category objects for DB.
     * @param recipe existing recipe to update
     * @param allCategoryIds new category IDs for this recipe
     * @return a list of category objects matching [allCategoryIds].
     */
    private fun getUpdatedCategoriesForRecipe(
            recipe: Recipe,
            allCategoryIds: List<Int>
    ): List<Category> {
        // Remove not used categories.
        val updatedCategories = recipe.categories.filter { allCategoryIds.contains(it.id) }.toMutableList()

        // Create a list of new categories.
        val newCategories = allCategoryIds.filter { newCategoryId ->
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

        return updatedCategories
    }

    /**
     * Update recipe's ingredients in the database. Also return the updated recipe-ingredients list.
     * @param recipe recipe to update
     * @param newIngredients new ingredient data (data that are not in this list will be removed)
     * @return updated list of recipe-ingredients
     */
    private fun updateRecipeIngredients(
            recipe: Recipe,
            newIngredients: List<UpdateRecipeIngredientDto>
    ): List<RecipeIngredient> {
        val currentIngredients = recipeIngredientRepository.findAllByIdentityRecipeId(recipe.id)

        // Remove not used ingredients.
        val removedIngredients = currentIngredients.filterNot { ingredient ->
            newIngredients.any { it.ingredientId == ingredient.identity.ingredientId }
        }

        var updatedRecipeIngredients = currentIngredients - removedIngredients
        // Update existing ingredients.
        updatedRecipeIngredients = updatedRecipeIngredients.map { current ->
            val newData = newIngredients.first { it.ingredientId == current.identity.ingredientId }
            RecipeIngredient(current.identity, newData.amount)
        }

        // Add new ingredients.
        val newData = newIngredients.filterNot { dto ->
            updatedRecipeIngredients.any { it.identity.ingredientId == dto.ingredientId }
        }.map { dto ->
            val identity = RecipeIngredientIdentity(
                    ingredientId = dto.ingredientId,
                    recipeId = recipe.id
            )
            RecipeIngredient(identity, dto.amount)
        }

        if (newData.isEmpty() && updatedRecipeIngredients.isEmpty()) {
            throw IllegalStateException("Ingredients cannot become empty!")
        }

        // Physically remove data after all checks have passed.
        recipeIngredientRepository.deleteAll(removedIngredients)

        // Store result in database.
        return recipeIngredientRepository.saveAll(updatedRecipeIngredients + newData)
    }

    override fun deleteRecipe(recipeId: Int, currentUser: User) {
        val recipe = recipeRepository.findById(recipeId).orElseThrow()
        // Only author can delete the recipe.
        if (recipe.author.id != currentUser.id) {
            throw AccessDeniedException("Not allowed to delete this recipe!")
        }
        recipeRepository.delete(recipe)
    }

    private fun Recipe.toDto(
            recipeIngredients: List<RecipeIngredient>,
            ingredients: List<Ingredient>?
    ): RecipeDto {
        val internalIngredients: List<Ingredient>
        internalIngredients = if (ingredients == null) {
            val allIds = recipeIngredients.map { it.identity.ingredientId }
            ingredientRepository.findAllByIdIn(allIds)
        } else {
            ingredients
        }

        val ingredientsDto = recipeIngredients.map { recipeIngredient ->
            RecipeIngredientDto(
                    ingredient = internalIngredients.first { it.id == recipeIngredient.identity.ingredientId },
                    amount = recipeIngredient.amount
            )
        }

        return RecipeDto(
                id = id,
                ingredients = ingredientsDto,
                categories = categories,
                name = name,
                author = author,
                description = description,
                instructions = instructions,
                preparationTime = preparationTime
        )
    }
}