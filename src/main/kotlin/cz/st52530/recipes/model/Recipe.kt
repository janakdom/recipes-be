package cz.st52530.recipes.model

import cz.st52530.recipes.model.base.BaseModel
import java.util.*
import javax.persistence.*

@Entity(name = "recipe")
data class Recipe(
        @Column(
                nullable = false,
                length = 45
        )
        val name: String,

        @Column(
                nullable = false,
                length = 1000
        )
        val description: String,

        @Column(
                nullable = false,
                columnDefinition = "TEXT"
        )
        val instructions: String,

        @Column(
                name = "created_at",
                nullable = false
        )
        val createdAt: Date,

        @JoinColumn(
                name = "author_id",
                nullable = false
        )
        @ManyToOne
        val author: User
) : BaseModel()