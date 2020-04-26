package cz.st52530.recipes.model

import java.util.*
import javax.persistence.*

@Entity(name = "recipe")
data class Recipe(
        @Id
        @GeneratedValue
        val id: Int,
        val name: String,
        val description: String,
        val instructions: String,
        @Column(name = "created_at")
        val createdAt: Date,
        @JoinColumn(
                name = "author_id",
                nullable = false
        )
        @ManyToOne
        val user: User
)