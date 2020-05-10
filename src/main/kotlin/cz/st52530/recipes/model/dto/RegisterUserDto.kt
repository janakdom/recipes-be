package cz.st52530.recipes.model.dto

data class RegisterUserDto(
        val username: String,
        val password: String,
        val displayName: String?
)