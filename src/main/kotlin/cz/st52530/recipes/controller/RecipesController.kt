package cz.st52530.recipes.controller

import cz.st52530.recipes.config.SWAGGER_AUTH_KEY
import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.model.dto.RecipeDto
import cz.st52530.recipes.model.dto.UpdateRecipeDto
import cz.st52530.recipes.security.JwtRequestFilter
import cz.st52530.recipes.service.IRecipeService
import cz.st52530.recipes.service.IUserService
import cz.st52530.recipes.util.JwtTokenUtil
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recipes/")
@SecurityRequirement(name = SWAGGER_AUTH_KEY)
class RecipesController(
        private val userService: IUserService,
        private val recipesService: IRecipeService,
        private val jwtTokenUtil: JwtTokenUtil
) {

    @GetMapping
    fun getUsersRecipes(@RequestHeader(JwtRequestFilter.AUTHORIZATION_HEADER) tokenHeader: String): List<Recipe> {
        val username = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.extractBareToken(tokenHeader))
        val user = userService.getUserByUsername(username)
        return recipesService.getByUser(user)
    }

    @GetMapping("{id}")
    fun getRecipe(
            @RequestHeader(JwtRequestFilter.AUTHORIZATION_HEADER) tokenHeader: String,
            @PathVariable("id") id: Int
    ): RecipeDto {
        val username = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.extractBareToken(tokenHeader))
        val user = userService.getUserByUsername(username)
        return recipesService.getById(id, user)
    }

    @PostMapping
    fun addRecipe(
            @RequestHeader(JwtRequestFilter.AUTHORIZATION_HEADER) tokenHeader: String,
            @RequestBody body: UpdateRecipeDto
    ): RecipeDto {
        val username = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.extractBareToken(tokenHeader))
        val user = userService.getUserByUsername(username)
        return recipesService.addRecipe(body, user)
    }

    @PutMapping("{id}")
    fun updateRecipe(
            @RequestHeader(JwtRequestFilter.AUTHORIZATION_HEADER) tokenHeader: String,
            @RequestBody body: UpdateRecipeDto,
            @PathVariable("id") id: Int
    ): RecipeDto {
        val username = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.extractBareToken(tokenHeader))
        val user = userService.getUserByUsername(username)
        return recipesService.updateRecipe(id, body, user)
    }

    @DeleteMapping("{id}")
    fun deleteRecipe(
            @RequestHeader(JwtRequestFilter.AUTHORIZATION_HEADER) tokenHeader: String,
            @PathVariable("id") id: Int
    ) {
        val username = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.extractBareToken(tokenHeader))
        val user = userService.getUserByUsername(username)
        recipesService.deleteRecipe(id, user)
    }
}