package cz.st52530.recipes.controller

import cz.st52530.recipes.config.SWAGGER_AUTH_KEY
import cz.st52530.recipes.model.database.Category
import cz.st52530.recipes.model.dto.UpdateCategoryDto
import cz.st52530.recipes.service.ICategoryService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
@SecurityRequirement(name = SWAGGER_AUTH_KEY)
class CategoryController(
        private val categoryService: ICategoryService
) {

    @GetMapping
    fun getAll(): List<Category> {
        return categoryService.getAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Int): Category {
        return categoryService.getById(id)
    }

    @PostMapping
    fun addCategory(@RequestBody body: UpdateCategoryDto): Category {
        return categoryService.addCategory(body)
    }

    @PutMapping("/{id}")
    fun updateCategory(
            @PathVariable("id") id: Int,
            @RequestBody body: UpdateCategoryDto
    ): Category {
        return categoryService.updateCategory(id, body)
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(
            @PathVariable("id") id: Int
    ) {
        categoryService.deleteCategory(id)
    }
}