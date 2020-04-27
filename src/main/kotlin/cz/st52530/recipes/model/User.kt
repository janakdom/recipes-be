package cz.st52530.recipes.model

import cz.st52530.recipes.model.base.BaseModel
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
                length = 60
        )
        var password: String,

        @Column(
                name = "display_name",
                nullable = true,
                length = 45
        )
        val displayName: String?
) : BaseModel()