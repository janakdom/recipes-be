package cz.st52530.recipes.controller

import cz.st52530.recipes.config.SWAGGER_AUTH_KEY
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SecurityRequirement(name = SWAGGER_AUTH_KEY)
class DummyController {

    @GetMapping("/")
    fun getRoot(): String {
        return "It works!"
    }
}