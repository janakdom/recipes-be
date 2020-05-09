package cz.st52530.recipes.model.dto

import cz.st52530.recipes.model.database.User

data class LoginResponse(
        val token: String,
        val user: User
)