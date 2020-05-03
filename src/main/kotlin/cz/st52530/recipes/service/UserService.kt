package cz.st52530.recipes.service

import cz.st52530.recipes.dao.UserRepository
import cz.st52530.recipes.model.database.User
import cz.st52530.recipes.model.security.UserDetailsModel
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userRepository: UserRepository
) : IUserService {

    override fun getUserById(id: Int): User {
        return userRepository.findById(id).orElseThrow()
    }

    override fun getUserByUsername(username: String): User {
        return userRepository.findByUsername(username)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
        return UserDetailsModel(
                _username = user.username,
                _password = user.password
        )
    }
}