package cz.st52530.recipes.dao

import cz.st52530.recipes.model.RecipeIngredient
import cz.st52530.recipes.model.id.RecipeIngredientIdentity
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeIngredientRepository : JpaRepository<RecipeIngredient, RecipeIngredientIdentity>