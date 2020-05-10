package cz.st52530.recipes.controller

import cz.st52530.recipes.model.dto.LoginResponse
import cz.st52530.recipes.model.dto.RegisterUserDto
import cz.st52530.recipes.service.IUserService
import cz.st52530.recipes.util.JwtTokenUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/register")
class RegistrationController(
        private val jwtTokenUtil: JwtTokenUtil,
        private val userService: IUserService
) {

    @PostMapping
    fun register(@RequestBody body: RegisterUserDto): LoginResponse {
        val user = userService.registerUser(body)

        val token = jwtTokenUtil.generateToken(userService.loadUserByUsername(user.username))
        return LoginResponse(token, user)
    }
}