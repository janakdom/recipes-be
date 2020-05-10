package cz.st52530.recipes.dao

import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.model.database.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository : JpaRepository<Recipe, Int> {

    fun findAllByAuthor(author: User, pageable: Pageable): Page<Recipe>

    fun findAllByAuthorAndNameContaining(author: User, name: String): List<Recipe>
}