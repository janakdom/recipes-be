package cz.st52530.recipes.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "user")
data class User(
        @Id
        @GeneratedValue
        val id: Int,
        val username: String,
        var password: String,
        @Column(name = "display_name")
        val displayName: String?
)