package cz.st52530.recipes.dao

import cz.st52530.recipes.model.database.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Int>