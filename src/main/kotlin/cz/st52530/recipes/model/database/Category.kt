package cz.st52530.recipes.model.database

import cz.st52530.recipes.model.database.base.BaseModel
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "category")
data class Category(
        @Column(
                nullable = false,
                length = 45,
                unique = true
        )
        val name: String
) : BaseModel()