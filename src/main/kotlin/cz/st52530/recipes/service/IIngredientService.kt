package cz.st52530.recipes.service

import cz.st52530.recipes.model.database.Ingredient
import cz.st52530.recipes.model.dto.UpdateIngredientDto

interface IIngredientService {

    fun getAll(): List<Ingredient>

    fun getById(id: Int): Ingredient

    fun addIngredient(ingredient: UpdateIngredientDto): Ingredient

    fun updateIngredient(id: Int, ingredient: UpdateIngredientDto): Ingredient

    fun deleteIngredient(id: Int)
}