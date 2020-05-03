package cz.st52530.recipes.model.database

import cz.st52530.recipes.model.database.base.BaseModel
import java.util.*
import javax.persistence.*

@Entity(name = "recipe")
data class Recipe(
        @Column(
                nullable = false,
                length = 45
        )
        var name: String,

        @Column(
                nullable = false,
                length = 1000
        )
        var description: String,

        @Column(
                nullable = false,
                length = 45,
                name = "preparation_time"
        )
        var preparationTime: String,

        @Lob
        @Column(
                nullable = false
        )
        var instructions: String,

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
        val author: User,

        @ManyToMany
        @JoinTable(
                name = "recipe_category",
                joinColumns = [JoinColumn(name = "recipe_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "category_id", referencedColumnName = "id")]
        )
        var categories: List<Category>
) : BaseModel()