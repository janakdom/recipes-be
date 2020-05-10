package cz.st52530.recipes.service

import cz.st52530.recipes.model.database.User
import cz.st52530.recipes.model.dto.RegisterUserDto
import org.springframework.security.core.userdetails.UserDetailsService

interface IUserService : UserDetailsService {

    fun getUserById(id: Int): User

    fun getUserByUsername(username: String): User

    fun registerUser(data: RegisterUserDto): User
}