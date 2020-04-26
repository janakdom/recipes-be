package cz.st52530.recipes.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "category")
data class Category(
        @Id
        @GeneratedValue
        val id: Int,
        val name: String
)