package cz.st52530.recipes.service

import cz.st52530.recipes.dao.IngredientRepository
import cz.st52530.recipes.model.database.Ingredient
import cz.st52530.recipes.model.dto.UpdateIngredientDto
import org.springframework.stereotype.Service

@Service
class IngredientService(
        private val ingredientRepository: IngredientRepository
) : IIngredientService {

    override fun getAll(): List<Ingredient> {
        return ingredientRepository.findAllByOrderByName()
    }

    override fun getById(id: Int): Ingredient {
        return ingredientRepository.findById(id).orElseThrow()
    }

    override fun addIngredient(ingredient: UpdateIngredientDto): Ingredient {
        val entity = Ingredient(ingredient.name)
        return ingredientRepository.save(entity)
    }

    override fun updateIngredient(id: Int, ingredient: UpdateIngredientDto): Ingredient {
        val entity = getById(id)
        entity.name = ingredient.name
        return ingredientRepository.save(entity)
    }

    override fun deleteIngredient(id: Int) {
        ingredientRepository.deleteById(id)
    }
}