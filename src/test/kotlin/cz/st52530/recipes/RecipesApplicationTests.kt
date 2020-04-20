package cz.st52530.recipes

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource(locations = ["classpath:application-test.properties"])
class RecipesApplicationTests {

    @Value("\${spring.flyway.enabled}")
    private var flywayEnabled: Boolean = true

    @Test
    fun whenContextLoads_thenPropertiesAssignedCorrectly() {
        assertFalse(flywayEnabled)
    }

}
