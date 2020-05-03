package cz.st52530.recipes.dao

import cz.st52530.recipes.model.database.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface CategoryRepository : JpaRepository<Category, Int> {

    fun findAllByIdIn(ids: Collection<Int>): List<Category>

    @Query("UPDATE category SET name=?2 WHERE id = ?1")
    @Modifying
    fun updateName(id: Int, name: String): Category
}