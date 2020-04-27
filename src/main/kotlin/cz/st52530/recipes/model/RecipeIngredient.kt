package cz.st52530.recipes.model

import cz.st52530.recipes.model.id.RecipeIngredientIdentity
import java.io.Serializable
import javax.persistence.*

@Entity(name = "recipe_ingredient")
data class RecipeIngredient(
        @EmbeddedId
        val identity: RecipeIngredientIdentity,

        @Column(
                nullable = false,
                length = 45
        )
        val amount: String
) : Serializable