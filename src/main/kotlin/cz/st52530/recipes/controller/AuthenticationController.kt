package cz.st52530.recipes.controller

import cz.st52530.recipes.model.dto.LoginRequest
import cz.st52530.recipes.model.dto.LoginResponse
import cz.st52530.recipes.service.IUserService
import cz.st52530.recipes.util.JwtTokenUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(
        private val authenticationManager: AuthenticationManager,
        private val jwtTokenUtil: JwtTokenUtil,
        private val userService: IUserService
) {

    @PostMapping("/api/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: LoginRequest): LoginResponse {
        authenticate(authenticationRequest.username, authenticationRequest.password)
        val userDetails: UserDetails = userService.loadUserByUsername(authenticationRequest.username)

        val token = jwtTokenUtil.generateToken(userDetails)
        return LoginResponse(token, userService.getUserByUsername(authenticationRequest.username))
    }

    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", e)
        }
    }
}