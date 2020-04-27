package cz.st52530.recipes.dao

import cz.st52530.recipes.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int>