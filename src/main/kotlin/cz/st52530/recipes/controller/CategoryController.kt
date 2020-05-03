package cz.st52530.recipes.controller

import cz.st52530.recipes.config.SWAGGER_AUTH_KEY
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categories/")
@SecurityRequirement(name = SWAGGER_AUTH_KEY)
class CategoryController(

) {

    @GetMapping
    fun getAll() {

    }
}