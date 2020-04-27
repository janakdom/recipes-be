package cz.st52530.recipes.model

import java.io.Serializable
import javax.persistence.*

@Entity(name = "recipe_ingredient")
data class RecipeIngredient(
        @Id
        @JoinColumn(
                name = "recipe_id",
                nullable = false
        )
        @ManyToOne
        val recipe: Recipe,

        @Id
        @JoinColumn(
                name = "ingredient_id",
                nullable = false
        )
        @ManyToOne
        val ingredient: Ingredient,

        @Column(
                nullable = false,
                length = 45
        )
        val amount: String
) : Serializable