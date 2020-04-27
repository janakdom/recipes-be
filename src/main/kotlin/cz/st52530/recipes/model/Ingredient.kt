package cz.st52530.recipes.model

import cz.st52530.recipes.model.base.BaseModel
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "ingredient")
data class Ingredient(
        @Column(
                nullable = false,
                length = 45,
                unique = true
        )
        val name: String
) : BaseModel()