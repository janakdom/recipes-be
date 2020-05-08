package cz.st52530.recipes.model.database

import cz.st52530.recipes.model.database.base.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "recipe_instruction")
data class Instruction(
        @Column(
                nullable = false,
                length = 1000
        )
        var text: String,

        @Column(
                nullable = false,
                name = "recipe_id"
        )
        val recipeId: Int
) : BaseEntity()