package cz.st52530.recipes.model

import javax.persistence.*

@Entity(name = "ingredient")
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
        val amount: String
)