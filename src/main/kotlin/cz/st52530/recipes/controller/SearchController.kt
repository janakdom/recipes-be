package cz.st52530.recipes.controller

import cz.st52530.recipes.config.SWAGGER_AUTH_KEY
import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.model.dto.SearchDto
import cz.st52530.recipes.security.JwtRequestFilter
import cz.st52530.recipes.service.IRecipeService
import cz.st52530.recipes.service.IUserService
import cz.st52530.recipes.util.JwtTokenUtil
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/search")
@SecurityRequirement(name = SWAGGER_AUTH_KEY)
class SearchController(
        private val userService: IUserService,
        private val recipesService: IRecipeService,
        private val jwtTokenUtil: JwtTokenUtil
) {

    @PostMapping
    fun searchRecipes(
            @RequestHeader(JwtRequestFilter.AUTHORIZATION_HEADER) tokenHeader: String,
            @RequestBody body: SearchDto
    ): List<Recipe> {
        val username = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.extractBareToken(tokenHeader))
        val user = userService.getUserByUsername(username)
        return recipesService.findRecipes(body, user)
    }
}