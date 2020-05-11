package cz.st52530.recipes.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import cz.st52530.recipes.dao.*
import cz.st52530.recipes.model.database.Category
import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.model.database.User
import cz.st52530.recipes.model.dto.RecipeDto
import cz.st52530.recipes.model.dto.UpdateRecipeDto
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.security.access.AccessDeniedException
import java.lang.IllegalArgumentException
import java.util.*

class RecipeServiceTest {

    private lateinit var underTest: RecipeService

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @Mock
    private lateinit var ingredientRepository: IngredientRepository

    @Mock
    private lateinit var instructionsRepository: InstructionsRepository

    @Mock
    private lateinit var recipeIngredientRepository: RecipeIngredientRepository

    @Mock
    private lateinit var recipeRepository: RecipeRepository

    private val currentUser: User = User(
            username = "username",
            password = "passwordHash",
            displayName = null
    )

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        underTest = RecipeService(
                categoryRepository = categoryRepository,
                ingredientRepository = ingredientRepository,
                instructionsRepository = instructionsRepository,
                recipeIngredientRepository = recipeIngredientRepository,
                recipeRepository = recipeRepository
        )
    }

    @Test
    fun whenRequestedByAuthor_thenGetByIdReturnsRecipe() {
        val mockRecipe = Recipe(
                author = currentUser,
                imageUrl = "http://image.url",
                categories = emptyList(),
                preparationTime = "Preparation",
                description = "Description",
                name = "Name",
                instructions = emptyList(),
                createdAt = Date()
        )
        whenever(recipeRepository.findById(1)).thenReturn(Optional.of(mockRecipe))

        val result = underTest.getById(1, currentUser)

        val expected = RecipeDto(
                id = -1,
                name = "Name",
                instructions = emptyList(),
                description = "Description",
                preparationTime = "Preparation",
                categories = emptyList(),
                imageUrl = "http://image.url",
                author = currentUser,
                ingredients = emptyList()
        )
        assertEquals(expected, result)
    }

    @Test
    fun whenRequestedByWrongUser_thenGetByIdThrowsException() {
        val wrongUser = mock<User>()
        whenever(wrongUser.id).thenReturn(5)

        val mockRecipe = Recipe(
                author = wrongUser,
                imageUrl = "http://image.url",
                categories = emptyList(),
                preparationTime = "Preparation",
                description = "Description",
                name = "Name",
                instructions = emptyList(),
                createdAt = Date()
        )
        whenever(recipeRepository.findById(1)).thenReturn(Optional.of(mockRecipe))

        assertThrows(AccessDeniedException::class.java) {
            underTest.getById(1, currentUser)
        }
    }

    @Test
    fun whenRecipeDoesNotExist_thenGetByIdThrowsException() {
        whenever(recipeRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            underTest.getById(1, currentUser)
        }
    }

    @Test
    fun givenWrongCategories_thenAddRecipeThrowsException() {
        val mockCategories = listOf(
                Category("Category 1")
        )
        whenever(categoryRepository.findAllByIdIn(any())).thenReturn(mockCategories)

        val addData = UpdateRecipeDto(
                name = "Name",
                ingredients = emptyList(),
                categories = listOf(1, 2),
                preparationTime = "Prep time",
                description = "Description",
                instructions = emptyList()
        )

        assertThrows(IllegalArgumentException::class.java) {
            underTest.addRecipe(addData, currentUser)
        }
    }

    @Test
    fun givenEmptyCategories_thenAddRecipeThrowsException() {
        whenever(categoryRepository.findAllByIdIn(any())).thenReturn(emptyList())

        val addData = UpdateRecipeDto(
                name = "Name",
                ingredients = emptyList(),
                categories = emptyList(),
                preparationTime = "Prep time",
                description = "Description",
                instructions = emptyList()
        )

        assertThrows(IllegalArgumentException::class.java) {
            underTest.addRecipe(addData, currentUser)
        }
    }

    @Test
    fun xx() {

    }

    @Test
    fun updateRecipeImage() {
    }

    @Test
    fun updateRecipe() {
    }

    @Test
    fun deleteRecipe() {
    }

    @Test
    fun findRecipes() {
    }
}