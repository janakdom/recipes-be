package cz.st52530.recipes.service

import cz.st52530.recipes.model.database.Category
import cz.st52530.recipes.model.database.Ingredient
import cz.st52530.recipes.model.database.Instruction
import cz.st52530.recipes.model.database.User
import cz.st52530.recipes.model.dto.RecipeDto
import cz.st52530.recipes.model.dto.UpdateRecipeDto
import cz.st52530.recipes.model.dto.UpdateRecipeIngredientDto
import cz.st52530.recipes.testutil.Creator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

@DataJpaTest
@Import([RecipeService.class, Creator.class])
class RecipeServiceIntegrationTest extends Specification {

    @Autowired
    private RecipeService recipeService

    @Autowired
    private Creator creator

    def "given required data exists then add recipe is successful"() {
        given: "Required data exists"
        User user = creator.saveEntity(new User("username", "pass", "My name"))
        Category category = creator.saveEntity(new Category("Chicken category"))
        Ingredient ingredient = creator.saveEntity(new Ingredient("Chicken breasts"))
        UpdateRecipeDto dto = new UpdateRecipeDto(
                "Recipe name",
                "Recipe description",
                "45 minutes",
                [new Instruction("Instruction", -1)],
                [category.id],
                [new UpdateRecipeIngredientDto(ingredient.id, "1 kg")]
        )

        when: "Add recipe called"
        RecipeDto result = recipeService.addRecipe(dto, user)

        then: "Recipe was created"
        result.name == "Recipe name"
    }

    def "given invalid input data then add recipe throws exception"() {
        given: "Invalid input data prepared"
        User user = creator.saveEntity(new User("username", "pass", "My name"))
        UpdateRecipeDto dto = new UpdateRecipeDto(
                "Recipe name",
                "Recipe description",
                "45 minutes",
                [],
                [2],
                []
        )

        when: "Add recipe called"
        recipeService.addRecipe(dto, user)

        then: "Exception is thrown"
        thrown(IllegalArgumentException.class)
    }
}
