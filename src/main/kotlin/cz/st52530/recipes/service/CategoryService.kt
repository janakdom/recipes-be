package cz.st52530.recipes.service

import cz.st52530.recipes.dao.CategoryRepository
import cz.st52530.recipes.model.database.Category
import cz.st52530.recipes.model.dto.UpdateCategoryDto
import org.springframework.stereotype.Service

@Service
class CategoryService(
        private val categoryRepository: CategoryRepository
) : ICategoryService {

    override fun getAll(): List<Category> {
        return categoryRepository.findAll()
    }

    override fun getById(id: Int): Category {
        return categoryRepository.findById(id).orElseThrow()
    }

    override fun addCategory(category: UpdateCategoryDto): Category {
        val entity = Category(
                name = category.name
        )
        return categoryRepository.save(entity)
    }

    override fun updateCategory(id: Int, category: UpdateCategoryDto): Category {
        return categoryRepository.updateName(id, category.name)
    }

    override fun deleteCategory(id: Int) {
        categoryRepository.deleteById(id)
    }
}