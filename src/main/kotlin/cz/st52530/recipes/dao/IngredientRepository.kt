package cz.st52530.recipes.dao

import cz.st52530.recipes.model.database.Ingredient
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository : JpaRepository<Ingredient, Int> {

    fun findAllByIdIn(ids: Collection<Int>): List<Ingredient>
}