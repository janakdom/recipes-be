package cz.st52530.recipes.dao

import cz.st52530.recipes.model.Recipe
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository : JpaRepository<Recipe, Int>