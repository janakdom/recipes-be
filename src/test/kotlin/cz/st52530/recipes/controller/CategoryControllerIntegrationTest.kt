package cz.st52530.recipes.controller

import cz.st52530.recipes.model.database.Category
import cz.st52530.recipes.testutil.Creator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import javax.transaction.Transactional

@SpringBootTest
@Transactional
class CategoryControllerIntegrationTest {

    @Autowired
    private lateinit var underTest: CategoryController

    @Autowired
    private lateinit var creator: Creator

    @Test
    fun givenNoCategoryExists_thenGetCategoriesReturnsEmptyList() {
        val result = underTest.getAll()
        assertEquals(0, result.size)
    }

    @Test
    fun givenTwoCategoriesExist_thenGetCategoriesReturnsCorrectData() {
        val category1 = Category("My category")
        val category2 = Category("Second category")
        creator.saveEntities(category1, category2)

        val result = underTest.getAll()
        assertEquals(2, result.size)
        assertEquals("My category", result[0].name)
        assertEquals("Second category", result[1].name)
    }

    @Test
    fun givenWrongCategoryId_thenGetCategoryThrowsError() {
        assertThrows(NoSuchElementException::class.java) {
            underTest.getById(1)
        }
    }

    @Test
    fun givenCorrectCategoryId_thenGetCategoryReturnsData() {
        val entity = creator.saveEntity(Category("Category name"))
        val result = underTest.getById(entity.id)
        assertEquals("Category name", result.name)
    }
}