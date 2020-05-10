package cz.st52530.recipes.controller

import com.fasterxml.jackson.databind.ObjectMapper
import cz.st52530.recipes.config.SWAGGER_AUTH_KEY
import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.model.dto.RecipeDto
import cz.st52530.recipes.model.dto.UpdateRecipeDto
import cz.st52530.recipes.security.JwtRequestFilter
import cz.st52530.recipes.service.IRecipeService
import cz.st52530.recipes.service.IUserService
import cz.st52530.recipes.util.ImageHandlingUtil
import cz.st52530.recipes.util.JwtTokenUtil
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/recipes")
@SecurityRequirement(name = SWAGGER_AUTH_KEY)
class RecipesController(
        private val userService: IUserService,
        private val recipesService: IRecipeService,
        private val jwtTokenUtil: JwtTokenUtil,
        private val imageHandlingUtil: ImageHandlingUtil
) {

    private val mapper by lazy {
        ObjectMapper()
    }

    @GetMapping
    fun getUsersRecipes(
            @RequestHeader(JwtRequestFilter.AUTHORIZATION_HEADER) tokenHeader: String,
            pageable: Pageable
    ): Page<Recipe> {
        val username = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.extractBareToken(tokenHeader))
        val user = userService.getUserByUsername(username)
        return recipesService.getByUser(user, pageable)
    }

    @GetMapping("/{id}")
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
            @RequestParam(name = "recipe") model: String,
            @RequestParam(name = "file", required = false) file: MultipartFile?
    ): RecipeDto {
        val recipe: UpdateRecipeDto = mapper.readValue(model, UpdateRecipeDto::class.java)
        val username = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.extractBareToken(tokenHeader))
        val user = userService.getUserByUsername(username)

        val imageUrl = if (file != null) imageHandlingUtil.uploadImage(file) else null
        return recipesService.addRecipe(recipe, imageUrl, user)
    }

    @PutMapping("/{id}")
    fun updateRecipe(
            @RequestHeader(JwtRequestFilter.AUTHORIZATION_HEADER) tokenHeader: String,
            @PathVariable("id") id: Int,
            @RequestParam(name = "recipe") model: String,
            @RequestParam(name = "file", required = false) file: MultipartFile?
    ): RecipeDto {
        val recipe: UpdateRecipeDto = mapper.readValue(model, UpdateRecipeDto::class.java)
        val username = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.extractBareToken(tokenHeader))
        val user = userService.getUserByUsername(username)

        val imageUrl = if (file != null) imageHandlingUtil.uploadImage(file, id) else null
        return recipesService.updateRecipe(id, recipe, imageUrl, user)
    }

    @DeleteMapping("/{id}")
    fun deleteRecipe(
            @RequestHeader(JwtRequestFilter.AUTHORIZATION_HEADER) tokenHeader: String,
            @PathVariable("id") id: Int
    ) {
        val username = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.extractBareToken(tokenHeader))
        val user = userService.getUserByUsername(username)
        recipesService.deleteRecipe(id, user)
    }
}