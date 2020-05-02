package cz.st52530.recipes.service

import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.model.database.User

interface IRecipeService {

    fun getByUser(user: User): List<Recipe>

    fun getById(id: Int): Recipe
}