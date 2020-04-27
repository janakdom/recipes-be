package cz.st52530.recipes.model.database

import cz.st52530.recipes.model.database.base.BaseModel
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "user")
data class User(
        @Column(
                nullable = false,
                length = 45,
                unique = true
        )
        val username: String,

        @Column(
                nullable = false,
                length = 60,
                columnDefinition = "CHAR"
        )
        var password: String,

        @Column(
                name = "display_name",
                nullable = true,
                length = 45
        )
        val displayName: String?
) : BaseModel()