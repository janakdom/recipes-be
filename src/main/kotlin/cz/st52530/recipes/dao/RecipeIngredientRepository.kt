package cz.st52530.recipes.dao

import cz.st52530.recipes.model.database.RecipeIngredient
import cz.st52530.recipes.model.database.id.RecipeIngredientIdentity
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeIngredientRepository : JpaRepository<RecipeIngredient, RecipeIngredientIdentity>