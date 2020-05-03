package cz.st52530.recipes.controller

import cz.st52530.recipes.config.SWAGGER_AUTH_KEY
import cz.st52530.recipes.model.database.Ingredient
import cz.st52530.recipes.model.dto.UpdateIngredientDto
import cz.st52530.recipes.service.IIngredientService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ingredients/")
@SecurityRequirement(name = SWAGGER_AUTH_KEY)
class IngredientController(
        private val ingredientService: IIngredientService
) {

    @GetMapping
    fun getAll(): List<Ingredient> {
        return ingredientService.getAll()
    }

    @GetMapping("{id}")
    fun getById(@PathVariable("id") id: Int): Ingredient {
        return ingredientService.getById(id)
    }

    @PostMapping
    fun addCategory(@RequestBody body: UpdateIngredientDto): Ingredient {
        return ingredientService.addIngredient(body)
    }

    @PutMapping("{id}")
    fun updateCategory(
            @PathVariable("id") id: Int,
            @RequestBody body: UpdateIngredientDto
    ): Ingredient {
        return ingredientService.updateIngredient(id, body)
    }

    @DeleteMapping("{id}")
    fun deleteCategory(
            @PathVariable("id") id: Int
    ) {
        ingredientService.deleteIngredient(id)
    }
}