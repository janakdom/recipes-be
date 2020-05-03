package cz.st52530.recipes.service

import cz.st52530.recipes.model.database.User
import org.springframework.security.core.userdetails.UserDetailsService

interface IUserService : UserDetailsService {

    fun getUserById(id: Int): User

    fun getUserByUsername(username: String): User
}