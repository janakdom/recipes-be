package cz.st52530.recipes.service

import cz.st52530.recipes.model.database.Category
import cz.st52530.recipes.model.dto.UpdateCategoryDto

interface ICategoryService {

    fun getAll(): List<Category>

    fun getById(id: Int): Category

    fun addCategory(category: UpdateCategoryDto): Category

    fun updateCategory(id: Int, category: UpdateCategoryDto): Category

    fun deleteCategory(id: Int)
}